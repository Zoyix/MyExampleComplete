package com.example.latte.core.delegates;

/**
 *
 * @author Administrator
 * @date 2017/11/14
 */

public abstract class LatteDelegate extends BaseDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
