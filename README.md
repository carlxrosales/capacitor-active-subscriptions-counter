# capacitor-active-subscriptions-counter

A lightweight Capacitor plugin that counts the number of active subscriptions on the device's App Store or Google Play account, independent of which user is logged into your app.

This helps developers detect if a store account already has an active subscription without restoring purchases or transferring ownership — solving a key limitation in mobile subscription workflows.

## 🎯 Purpose

In most subscription systems, there's no way to check if a store account has an active subscription without performing a restore operation.
However, calling `restorePurchases()` or equivalent SDK methods can unintentionally transfer ownership to a different user profile — especially in apps that support multiple accounts or sign-ins.

This plugin solves that by providing a read-only, count-only check for active subscriptions on the current store account.
No restores, no data transfer, no sensitive information.

## 💡 Use Cases

| Scenario                                             | Description                                                                                                              |
| ---------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **Multi-account apps**                               | Detect if the store account already has an active subscription before showing a paywall or starting a new purchase flow. |
| **RevenueCat or StoreKit/Play Billing integrations** | Prevent unexpected ownership transfers caused by `restorePurchases()`.                                                   |
| **Shared devices**                                   | Avoid duplicate subscriptions when multiple users share the same Apple or Google account.                                |
| **Subscription diagnostics**                         | Quickly check whether the device's store account has any active subscriptions for debugging or analytics.                |

## Install

```bash
npm install capacitor-active-subscriptions-counter
npx cap sync
```

## Compatibility

- **iOS**: 15.0+ (uses StoreKit 2)
- **Android**: API level 24+ (uses Google Play Billing Library 7.1.1)
- **Web**: Not supported

## Usage

```typescript
import { ActiveSubscriptionsCounter } from 'capacitor-active-subscriptions-counter';

// Get the count of active subscriptions
const { count } = await ActiveSubscriptionsCounter.getActiveSubscriptionsCount();
console.log(`Active subscriptions: ${count}`);
```

## API

<docgen-index>

* [`getActiveSubscriptionsCount()`](#getactivesubscriptionscount)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getActiveSubscriptionsCount()

```typescript
getActiveSubscriptionsCount() => Promise<{ count: number; }>
```

Get the count of active subscriptions

**Returns:** <code>Promise&lt;{ count: number; }&gt;</code>

--------------------

</docgen-api>
