# capacitor-active-subscriptions-counter

A lightweight Capacitor plugin that counts active subscriptions from the device's Google Play or App Store account, independent of which user is logged into your app.

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
