package com.jaredluo.rn.basic;

import android.app.Activity;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/5/23.
 */

public class ReactViewPreLoader {
    private static Map<String, ReactRootView> sCache = new HashMap<>();
    private static MutableContextWrapper mWrapper;

    public static synchronized void load(Context context, String... mainComponentNames) {
        if (mainComponentNames != null) {
            for (String compName : mainComponentNames) {
                if (!TextUtils.isEmpty(compName) && sCache.get(compName) == null) {
                    loadInternal(context, compName);
                }
            }
        }
    }

    public static ReactRootView getOrLoadReactRootView(Context context, String mainComponentName) {
        ReactRootView view = getReactRootView(context, mainComponentName);
        if (view != null) {
            return view;
        }
        return loadInternal(context, mainComponentName);
    }

    public static ReactRootView getReactRootView(Context context, String mainComponentName) {
        if (!TextUtils.isEmpty(mainComponentName)) {
            ReactRootView view = sCache.get(mainComponentName);
            if (view != null && !hasParent(view)) {
                mWrapper.setBaseContext(context);
                return view;
            }
        }
        return null;
    }

    private static ReactRootView loadInternal(Context context, String compName) {
        mWrapper = new MutableContextWrapper(context);
        ReactRootView reactRootView = createRootView(mWrapper);
        reactRootView.startReactApplication(
                getReactNativeHost((Activity) context).getReactInstanceManager(),
                compName,
                null);
        sCache.put(compName, reactRootView);
        return reactRootView;
    }

    private static boolean hasParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        return parent != null;
    }

    private static ReactRootView createRootView(Context context) {
        return new ReactRootView(context);
    }


    private static ReactNativeHost getReactNativeHost(Activity activity) {
        return ((ReactApplication) activity.getApplication()).getReactNativeHost();
    }
}
