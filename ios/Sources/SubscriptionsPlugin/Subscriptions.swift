import Foundation
import StoreKit

@available(iOS 15.0, *)
@objc public class ActiveSubscriptionsCounter: NSObject {
    
    @objc public func getActiveSubscriptionsCount() -> Int {
        return getActiveSubscriptions().count
    }
    
    private func getActiveSubscriptions() -> [Product.SubscriptionInfo] {
        var activeSubscriptions: [Product.SubscriptionInfo] = []
        
        let semaphore = DispatchSemaphore(value: 0)
        
        Task {
            do {
                for await result in Transaction.currentEntitlements {
                    if case .verified(let transaction) = result {
                        if transaction.productType == .autoRenewable {
                            if let product = try? await Product.products(for: [transaction.productID]).first {
                                if let subscription = product.subscription {
                                    let status = try await subscription.status
                                    for case .verified(let renewalInfo) in status {
                                        if renewalInfo.state == .subscribed || 
                                           renewalInfo.state == .inGracePeriod ||
                                           renewalInfo.state == .inBillingRetryPeriod {
                                            activeSubscriptions.append(subscription)
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch {
                print("Error getting subscriptions: \(error.localizedDescription)")
            }
            semaphore.signal()
        }
        
        let result = semaphore.wait(timeout: .now() + 10.0)
        if result == .timedOut {
            print("Warning: Subscription query timed out")
        }
        
        return activeSubscriptions
    }
}
