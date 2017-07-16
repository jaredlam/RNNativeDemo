package com.jaredluo.rn.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.CustomReactActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.jaredluo.rn.activity.ReactNativeActivity;



public class NavigationModule extends ReactContextBaseJavaModule {

    public static final String PARAMS_KEY = "nav";
    public static final String RESULT_PARAMS_KEY = "result";
    private static final int REQUEST_CODE = 0x100;

    private Callback mCallback;

    private final ActivityEventListener mListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (REQUEST_CODE == resultCode) {
                if (data != null) {
                    Bundle bundle = data.getBundleExtra(RESULT_PARAMS_KEY);
                    if (bundle != null) {
                        WritableMap writableMap = Arguments.fromBundle(bundle);
                        if (mCallback != null) {
                            mCallback.invoke(writableMap);
                        }
                    }
                }
            }
        }

        @Override
        public void onNewIntent(Intent intent) {

        }
    };


    public NavigationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mListener);
    }

    @Override
    public String getName() {
        return "NavigationModule";
    }


    @ReactMethod
    public void push(String componentName, ReadableMap params) {
        pushWithResult(componentName, params, null);
    }


    @ReactMethod
    public void pop() {
        popWithResult(null);
    }


    @ReactMethod
    public void pushToNative(String activityClassName, ReadableMap paramValue) {

        if (getCurrentActivity() != null) {

            Intent intent = new Intent();
            intent.setClassName(getCurrentActivity(), activityClassName);

            if (paramValue != null && paramValue.keySetIterator().hasNextKey()) {
                Bundle bundle = Arguments.toBundle(paramValue);
                intent.putExtras(bundle);
            }

            getCurrentActivity().startActivity(intent);
        }
    }

    @ReactMethod
    public void pushWithResult(String componentName, ReadableMap params, Callback callback) {
        mCallback = callback;

        Bundle bundle = Arguments.toBundle(params);

        Intent intent = new Intent(getCurrentActivity(), ReactNativeActivity.class);
        intent.putExtra(CustomReactActivity.COMPONENT_NAME_KEY, componentName);
        intent.putExtra(PARAMS_KEY, bundle);
        if (getCurrentActivity() != null) {
            if (mCallback != null) {
                getCurrentActivity().startActivityForResult(intent, REQUEST_CODE);
            } else {
                getCurrentActivity().startActivity(intent);
            }
        }
    }


    @ReactMethod
    public void popWithResult(ReadableMap params) {
        if (getCurrentActivity() != null) {
            if (params == null) {
                getCurrentActivity().finish();
            } else {
                Bundle bundle = Arguments.toBundle(params);

                Intent intent = new Intent();
                intent.putExtra(RESULT_PARAMS_KEY, bundle);
                getCurrentActivity().setResult(REQUEST_CODE, intent);
                getCurrentActivity().finish();
            }
        }
    }

}