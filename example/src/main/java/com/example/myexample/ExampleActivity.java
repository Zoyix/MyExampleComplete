package com.example.myexample;


import com.example.latte.core.activities.ProxyActivity;
import com.example.latte.core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

}
