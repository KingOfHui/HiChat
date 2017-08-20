package com.example.dh.hichat.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.dh.hichat.R;
import com.example.dh.hichat.ui.fragment.ContactFragment;
import com.example.dh.hichat.ui.fragment.ConversationFragment;
import com.example.dh.hichat.ui.fragment.LiveFragment;
import com.example.dh.hichat.ui.fragment.NearByFragment;
import com.example.dh.hichat.ui.fragment.PersonalFragment;
import com.example.dh.hichat.utils.FragmentFactory;
import com.example.dh.hichat.utils.MyLogger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contaier)
    FrameLayout contaier;
    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initBottomNavigationBar();
    }


    Class[] fragments=new Class[]{
            NearByFragment.class,
            LiveFragment.class,
            ConversationFragment.class,
            ContactFragment.class,
            PersonalFragment.class
    };
    //初始化Fragment
    private void initFragment() {
        //把NearByFragment显示在FrameLayout里面
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contaier, FragmentFactory.createFragment(fragments[0]), "0");//把附近的Fragment添加到container
        ft.commit();
    }

    //底部导航条图片资源
    int[] barIcons=new int[]{
           R.drawable.ic_nav_nearby_active,
           R.drawable.ic_nav_live_active,
           R.drawable.ic_nav_conversation_active,
           R.drawable.ic_nav_contacts_active,
           R.drawable.ic_nav_personal_active
    };
    //初始化底部导航条
    private void initBottomNavigationBar() {
        //底部导航的标签
        String[] barLabels = getResources().getStringArray(R.array.bottombarlabel);

        //未读消息提醒
        BadgeItem badgeItem = new BadgeItem();
//        badgeItem.setGravity(Gravity.TOP | Gravity.RIGHT);
        badgeItem.setText("10");
        badgeItem.hide();
        bottomNavigationBar
                .setBarBackgroundColor(R.color.bottombarcolor)
                .addItem(new BottomNavigationItem(barIcons[0],barLabels[0]))
                .addItem(new BottomNavigationItem(barIcons[1],barLabels[1]))
                .addItem(new BottomNavigationItem(barIcons[2],barLabels[2]).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(barIcons[3],barLabels[3]))
                .addItem(new BottomNavigationItem(barIcons[4],barLabels[4]))
                .setActiveColor(R.color.activecolor)
                .setInActiveColor(R.color.inactivecolor)
                .setFirstSelectedPosition(0)
                .setMode(BottomNavigationBar.MODE_FIXED)//设置为混合模式
                .initialise();
        //给Tab设置点击监听
        bottomNavigationBar.setTabSelectedListener(new MyTabSelectedListener());
    }

    private class MyTabSelectedListener implements BottomNavigationBar.OnTabSelectedListener {

        //显示Fragment
        @Override
        public void onTabSelected(int position) {
            MyLogger.i("onTabSelected:"+position);
            //1.通过FragmentManager获取2.如果不存在就通过FragmentFactory创建
            //3.如果存在就显示
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment==null) {
                //添加
                ft.add(R.id.contaier, FragmentFactory.createFragment(fragments[position]), position + "");

            }else {
                //显示
                ft.show(fragment);
            }
            ft.commit();
        }

        //隐藏Fragment
        @Override
        public void onTabUnselected(int position) {
            MyLogger.i("onTabUnselected:"+position);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();
            ft.hide(fragment);
            ft.commit();
        }

        @Override
        public void onTabReselected(int position) {
            MyLogger.i("onTabReselected:"+position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //把当前的activity移至到后台
            //是否为任务栈的
            moveTaskToBack(false);
            return true;//自己处理后退键的行为
        }
        return super.onKeyDown(keyCode, event);
    }
}
