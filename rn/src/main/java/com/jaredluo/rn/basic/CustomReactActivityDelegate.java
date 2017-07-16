package com.jaredluo.rn.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.CustomReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.jaredluo.rn.modules.NavigationModule;

import java.io.Serializable;

import javax.annotation.Nullable;


/**
 * Created by admin on 2017/5/23.
 */

public class CustomReactActivityDelegate extends ReactActivityDelegate {

    private final Activity mActivity;
    private final String mMainComponentName;
    private final FragmentActivity mFragmentActivity;


    public CustomReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
        mActivity = activity;
        mMainComponentName = mainComponentName;
        mFragmentActivity = null;
    }

    public CustomReactActivityDelegate(FragmentActivity fragmentActivity, @Nullable String mainComponentName) {
        super(fragmentActivity, mainComponentName);
        mActivity = null;
        mMainComponentName = mainComponentName;
        mFragmentActivity = fragmentActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadApp(String appKey) {
        ReactRootView rootView = ReactViewPreLoader.getReactRootView(mActivity, mMainComponentName);
        if (rootView != null && rootView.getParent() == null) {
            ((Activity) getContext()).setContentView(rootView);
        } else {
            super.loadApp(appKey);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        View rootView = ReactViewPreLoader.getReactRootView(mMainComponentName);
//        if (rootView != null) {
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if (parent != null) {
//                parent.removeView(rootView);
//            }
//        }
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return Assertions.assertNotNull(mFragmentActivity);
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        if (mActivity != null) {
            return mActivity.getIntent().getBundleExtra(NavigationModule.PARAMS_KEY);
        }
        return null;
    }
}
