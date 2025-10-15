package com.carlrosales.capacitor.subscriptions;

import android.content.Context;
import com.android.billingclient.api.*;
import com.getcapacitor.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class ActiveSubscriptionsCounter {
    
    private Context context;
    private BillingClient billingClient;
    private List<Purchase> activePurchases = new ArrayList<>();
    private CountDownLatch billingLatch;
    
    public ActiveSubscriptionsCounter(Context context) {
        this.context = context;
        initializeBillingClient();
    }
    
    public void cleanup() {
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
        }
    }

    private void initializeBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(new PurchasesUpdatedListener() {
                @Override
                public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                        for (Purchase purchase : purchases) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                                activePurchases.add(purchase);
                            }
                        }
                    }
                }
            })
            .enablePendingPurchases()
            .build();
    }

    public int getActiveSubscriptionsCount() {
        Logger.info("ActiveSubscriptionsCounter", "Getting active subscriptions count");
        queryActiveSubscriptions();
        return activePurchases.size();
    }
    
    private void queryActiveSubscriptions() {
        if (!billingClient.isReady()) {
            Logger.warn("ActiveSubscriptionsCounter", "Billing client not ready, attempting to start connection");
            startBillingConnection();
            return;
        }
        
        billingLatch = new CountDownLatch(1);
        activePurchases.clear();
        
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build(),
            new PurchasesResponseListener() {
                @Override
                public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> purchases) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : purchases) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                                if (isPurchaseValid(purchase)) {
                                    activePurchases.add(purchase);
                                }
                            }
                        }
                        Logger.info("ActiveSubscriptionsCounter", "Found " + activePurchases.size() + " active subscriptions");
                    } else {
                        Logger.warn("ActiveSubscriptionsCounter", "Failed to query purchases: " + billingResult.getDebugMessage());
                    }
                    billingLatch.countDown();
                }
            }
        );
        
        try {
            boolean completed = billingLatch.await(10, TimeUnit.SECONDS);
            if (!completed) {
                Logger.warn("ActiveSubscriptionsCounter", "Billing query timed out");
            }
        } catch (InterruptedException e) {
            Logger.error("ActiveSubscriptionsCounter", "Interrupted while waiting for billing query", e);
            Thread.currentThread().interrupt();
        }
    }
    
    private void startBillingConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Logger.info("ActiveSubscriptionsCounter", "Billing client connected successfully");
                    queryActiveSubscriptions();
                } else {
                    Logger.warn("ActiveSubscriptionsCounter", "Billing setup failed: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Logger.warn("ActiveSubscriptionsCounter", "Billing service disconnected");
            }
        });
    }
    
    private boolean isPurchaseValid(Purchase purchase) {
        if (purchase == null || purchase.getSkus().isEmpty()) {
            return false;
        }
        
        if (purchase.getPurchaseToken() == null || purchase.getPurchaseToken().isEmpty()) {
            return false;
        }
        
        if (purchase.getOrderId() == null || purchase.getOrderId().isEmpty()) {
            return false;
        }
        
        return true;
    }
}
