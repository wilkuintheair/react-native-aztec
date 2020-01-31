package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.BaseActivityEventListener;

/**
 * Created by Piotr Wilk on 2019-12-30.
 */
public class ImagePickerModule extends ReactContextBaseJavaModule {

    private static final int IMAGE_PICKER_REQUEST = 65530;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";

    private Promise mPickerPromise;

    ImagePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);

        // Add the listener for `onActivityResult`
        ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
                if (requestCode == IMAGE_PICKER_REQUEST) {
                    if (mPickerPromise != null) {
                        if (resultCode == Activity.RESULT_CANCELED) {
                            mPickerPromise.reject(E_PICKER_CANCELLED, "Image picker was cancelled");
                        } else if (resultCode == Activity.RESULT_OK) {
                            mPickerPromise.resolve(intent.getStringExtra("MESSAGE"));
                        } else {
                            mPickerPromise.resolve(resultCode);
                        }

                        mPickerPromise = null;
                    }
                }
            }
        };
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "ImagePickerModule";
    }

    @ReactMethod
    public void pickImage(final Promise promise) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        // Store the promise to resolve/reject when picker returns data
        mPickerPromise = promise;

        try {
            final Intent captureIntent = new Intent(currentActivity.getApplicationContext(), CaptureActivity.class);
            currentActivity.startActivityForResult(captureIntent, IMAGE_PICKER_REQUEST);
        } catch (Exception e) {
            mPickerPromise.reject(E_FAILED_TO_SHOW_PICKER, e);
            mPickerPromise = null;
        }
    }
}
