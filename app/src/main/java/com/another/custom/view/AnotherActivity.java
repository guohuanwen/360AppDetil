package com.another.custom.view;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by bigwen on 2016/1/18.
 */
public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Example(this));
    }
}
