package com.yingjie.addressselector.inner;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class SelectorFactory {

    private static ISelector iSelector = new SelectorImp();

    public SelectorFactory() {}

    public static ISelector create() {
        return iSelector;
    }
}
