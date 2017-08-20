package com.example.dh.hichat.utils;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Apple on 2016/12/1.
 * Fragment的工厂类
 */

public final class FragmentFactory {
    private FragmentFactory() {
    }
    private static Map<Class,Fragment> map = new HashMap<>();


    //获取实例
    public static synchronized Fragment getInstance(Class<? extends Fragment> clazz){
        //从集合里面获取
        Fragment fragment = map.get(clazz);
        if(fragment == null){
            try{
                fragment = clazz.newInstance();
                //保存进集合
                map.put(clazz,fragment);
            }catch (Exception e){

            }
        }
        return  fragment;
    }

    //清空
    public static void clear(){
        map.clear();
    }

}
