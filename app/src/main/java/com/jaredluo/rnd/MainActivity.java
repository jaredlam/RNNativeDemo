package com.jaredluo.rnd;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.facebook.react.CustomReactActivity;

import javax.annotation.Nullable;

/**
 * Created by Jared on 17/7/16.
 */
public class MainActivity extends CustomReactActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "Main";
    }
}
