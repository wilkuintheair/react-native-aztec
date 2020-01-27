package com.google.zxing.client.android;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.google.zxing.BarcodeFormat;

public class AztecModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public AztecModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Aztec";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke(BarcodeFormat.AZTEC + " Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
