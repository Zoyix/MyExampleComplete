package com.example.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by Administrator on 2017/11/13.
 */

public enum  EcIcons implements Icon {
    //原来的是"&#xe602;"
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    /**
     * 这里做了转换，因此在xml中使用的时候要用icon-scan
     * @return
     */
    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
