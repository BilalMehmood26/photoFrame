package com.example.naturephotoframe.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.example.naturephotoframe.R;

import java.util.ArrayList;
import java.util.List;

public class CheckPurchase {

    public static BillingClient billingClient;
    public static String ONE_WEEK, MONTHLY, THREE_MONTH,SIX_MONTHS,LIFETIME;
    public static boolean isPurchase = false, isBpClientReady = false;
    public static Context contextGlobal;
    public static SkuDetails week1Detail = null, week4detail = null, month3Detail = null, month6Details = null,lifetimeDetail = null;
    public static PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
//                updatePurchases(list);
                for (Purchase purchase : list) {
                    handlePurchase(purchase, contextGlobal);
                }

            } else {
                try {
                    if (!billingResult.getDebugMessage().isEmpty()) {
                        Toast.makeText(contextGlobal, billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public static void updatePurchases(List<Purchase> list) {
        List<Purchase> updatedPurchaseList = new ArrayList<>();
        updatedPurchaseList = list;
        if (updatedPurchaseList.size() > 0) {
            for (Purchase purchase : updatedPurchaseList) {
                if (purchase.getSku().equals(ONE_WEEK) || purchase.getSku().equals(MONTHLY) || purchase.getSku().equals(THREE_MONTH) || purchase.getSku().equals(LIFETIME)) {
                    isPurchase = true;
                    break;
                } else if (purchase.getSku().equals(THREE_MONTH) || purchase.getSku().equals(SIX_MONTHS) || purchase.getSku().equals(THREE_MONTH)) {
                    isPurchase = true;
                    break;
                }else {
                    isPurchase = false;
                }
            }
        } else {
            isPurchase = false;
        }
    }

    public static BillingClientStateListener billingClientStateListener = new BillingClientStateListener() {
        @Override
        public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                isBpClientReady = true;
                get_owned_purchases();
                query_subscriptions();
                query_products();
            }
            //   test in App for ads by un-commenting below line
//            isPurchase=true;
        }

        @Override
        public void onBillingServiceDisconnected() {

        }
    };

    public static void setup_billing_client(Context context) {
        contextGlobal = context;
        ONE_WEEK = context.getString(R.string.oneWeek);
        MONTHLY = context.getString(R.string.monthly);
        THREE_MONTH = context.getString(R.string.threeMonth);
        SIX_MONTHS = context.getString(R.string.sixMonth);
        LIFETIME = context.getString(R.string.lifeTime);

        billingClient = BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        if (!billingClient.isReady()) {
            billingClient.startConnection(billingClientStateListener);
        }
    }

    public static void launch_billing_flow(Activity activity, SkuDetails skuDetails) {
        Log.d("sku",skuDetails.getSku());
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        int responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).getResponseCode();
        if (responseCode == BillingClient.BillingResponseCode.OK) {

        } else if (responseCode == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE) {
            Toast.makeText(activity, "Billing Unavailable!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    public static void query_subscriptions() {
        List<String> skuListInApp = new ArrayList<>();
        skuListInApp.add(ONE_WEEK);
        skuListInApp.add(MONTHLY);
        skuListInApp.add(THREE_MONTH);
        skuListInApp.add(SIX_MONTHS);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuListInApp).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.
                        if (skuDetailsList.size() > 0) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                Log.d("inapplist", skuDetails.getTitle());
                                if (skuDetails.getSku().equals(ONE_WEEK)) {
                                    week1Detail = skuDetails;
                                } else if (skuDetails.getSku().equals(MONTHLY)) {
                                    week4detail = skuDetails;
                                } else if (skuDetails.getSku().equals(THREE_MONTH)) {
                                    month3Detail = skuDetails;
                                }else if (skuDetails.getSku().equals(SIX_MONTHS)) {
                                    month6Details = skuDetails;
                                }
                            }
                        } else {
                            week1Detail = null;
                            week4detail = null;
                            month3Detail = null;
                            month6Details = null;
                        }

                    }
                }
        );

    }

    public static void query_products() {
        List<String> skuListInApp = new ArrayList<>();
        skuListInApp.add(LIFETIME);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuListInApp).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.
                        if (skuDetailsList.size() > 0) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                Log.d("inapplist", skuDetails.getTitle());
                                if (skuDetails.getSku().equals(LIFETIME)) {
                                    lifetimeDetail = skuDetails;
                                }

                            }
                        } else {
                            lifetimeDetail = null;
                        }
                    }

                });

    }

    public static void get_owned_purchases() {
        List<Purchase> purchasesInApp, purchasesSub;
        purchasesInApp = billingClient.queryPurchases(BillingClient.SkuType.INAPP).getPurchasesList();
        purchasesSub = billingClient.queryPurchases(BillingClient.SkuType.SUBS).getPurchasesList();
        if (purchasesInApp != null) {
            for (Purchase purchase : purchasesInApp) {
                if (purchase.getSku().equals(LIFETIME)) {
                    isPurchase = true;
                    break;
                } else {
                    isPurchase = false;
                }
            }
        } else {
            isPurchase = false;
        }
        if (!isPurchase) {
            if (purchasesSub != null) {
                for (Purchase purchase : purchasesSub) {
                    if (purchase.getSku().equals(ONE_WEEK) || purchase.getSku().equals(MONTHLY) || purchase.getSku().equals(THREE_MONTH)) {
                        isPurchase = true;
                        break;
                    } else if (purchase.getSku().equals(THREE_MONTH) || purchase.getSku().equals(SIX_MONTHS) || purchase.getSku().equals(THREE_MONTH)) {
                        isPurchase = true;
                        break;
                    }else {
                        isPurchase = false;
                    }
                }
            } else {
                isPurchase = false;
            }
        }
    }

    public static void onPurchasedItemDialog(Context context) {
        get_owned_purchases();
    }

    public static void handlePurchase(Purchase purchase, Context context) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                    @Override
                    public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            // Handle the success of the consume operation.
                            onPurchasedItemDialog(context);
                        }
                    }
                };
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            } else {
                onPurchasedItemDialog(context);
            }
        }
    }


}
