import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@available(iOS 15.0, *)
@objc(ActiveSubscriptionsCounterPlugin)
public class ActiveSubscriptionsCounterPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ActiveSubscriptionsCounterPlugin"
    public let jsName = "ActiveSubscriptionsCounter"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getActiveSubscriptionsCount", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = ActiveSubscriptionsCounter()

    @objc func getActiveSubscriptionsCount(_ call: CAPPluginCall) {
        do {
            let count = implementation.getActiveSubscriptionsCount()
            call.resolve([
                "count": count
            ])
        } catch {
            call.reject("Failed to get subscription count: \(error.localizedDescription)")
        }
    }
    
}
