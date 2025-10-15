export interface ActiveSubscriptionsCounterPlugin {
  /**
   * Get the count of active subscriptions
   */
  getActiveSubscriptionsCount(): Promise<{ count: number }>;
}
