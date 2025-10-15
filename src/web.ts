import { WebPlugin } from '@capacitor/core';

import type { ActiveSubscriptionsCounterPlugin } from './definitions';

export class ActiveSubscriptionsCounterWeb extends WebPlugin implements ActiveSubscriptionsCounterPlugin {
  async getActiveSubscriptionsCount(): Promise<{ count: number }> {
    throw new Error('Active Subscriptions Counter plugin is not supported on web platform. Use iOS or Android.');
  }
}
