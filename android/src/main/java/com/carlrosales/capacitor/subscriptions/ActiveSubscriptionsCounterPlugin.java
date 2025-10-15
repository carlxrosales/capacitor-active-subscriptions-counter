package com.carlrosales.capacitor.subscriptions;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "ActiveSubscriptionsCounter")
public class ActiveSubscriptionsCounterPlugin extends Plugin {

    private ActiveSubscriptionsCounter implementation;
    
    @Override
    public void load() {
        implementation = new ActiveSubscriptionsCounter(getContext());
    }

    @PluginMethod
    public void getActiveSubscriptionsCount(PluginCall call) {
        try {
            int count = implementation.getActiveSubscriptionsCount();
            
            JSObject ret = new JSObject();
            ret.put("count", count);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Failed to get subscription count: " + e.getMessage());
        }
    }

}
