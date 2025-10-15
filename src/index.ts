import { registerPlugin } from '@capacitor/core';

import type { ActiveSubscriptionsCounterPlugin } from './definitions';

const ActiveSubscriptionsCounter = registerPlugin<ActiveSubscriptionsCounterPlugin>('ActiveSubscriptionsCounter', {
  web: () => import('./web').then((m) => new m.ActiveSubscriptionsCounterWeb()),
});

export * from './definitions';
export { ActiveSubscriptionsCounter };
