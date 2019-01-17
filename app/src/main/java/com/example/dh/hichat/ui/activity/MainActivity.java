package com.example.dh.hichat.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.dh.hichat.ExampleUtil;
import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;
import com.example.dh.hichat.ui.fragment.ConversationFragment;
import com.example.dh.hichat.ui.fragment.LiveFragment;
import com.example.dh.hichat.ui.fragment.NearByFragment;
import com.example.dh.hichat.ui.fragment.PersonalFragment;
import com.example.dh.hichat.utils.FragmentFactory;
import com.example.dh.hichat.utils.MyLogger;
import com.example.dh.hichat.utils.NetStatusUtil;
import com.example.dh.hichat.weight.NetLoadingDialog;
import com.example.dh.hichat.weight.TipDialog;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contaier)
    FrameLayout contaier;
    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        registerMessageReceiver();
        initFragment();
        initBottomNavigationBar();
        loading();
//        checkUpdate();
    }


    public void loading() {
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
            }
        }, 1500);
    }

    private NetLoadingDialog loadingDialog;
    /**
     * 显示弹出框默认一个按钮
     *
     * @param msg 提示信息
     */
    public void showDlg(String msg) {
        TipDialog dialog = new TipDialog(this);
        dialog.setType(TipDialog.TYPE_ONE_BUTTON);
        dialog.setMessage(msg);
        dialog.show();
    }
    public NetLoadingDialog getLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new NetLoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setCancelable(false);
        }
        return loadingDialog;
    }

    public void showLoading() {
        if (!getLoadingDialog().isShowing()) {
            getLoadingDialog().show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        checkUpdate();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    Class[] fragments = new Class[]{
            NearByFragment.class,
            LiveFragment.class,
            ConversationFragment.class,
//            ContactFragment.class,
            PersonalFragment.class
    };

    //初始化Fragment
    private void initFragment() {
        //把NearByFragment显示在FrameLayout里面
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contaier, FragmentFactory.getInstance(fragments[0]), "0");//把附近的Fragment添加到container
        ft.commit();
    }

    //底部导航条图片资源
    int[] barIcons = new int[]{
            R.drawable.ic_nav_nearby_active,
            R.drawable.ic_nav_live_active,
            R.drawable.ic_nav_conversation_active,
//            R.drawable.ic_nav_contacts_active,
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
                .addItem(new BottomNavigationItem(barIcons[0], barLabels[0]))
                .addItem(new BottomNavigationItem(barIcons[1], barLabels[1]))
                .addItem(new BottomNavigationItem(barIcons[2], barLabels[2]).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(barIcons[3], barLabels[3]))
//                .addItem(new BottomNavigationItem(barIcons[4], barLabels[4]))
                .setActiveColor(R.color.activecolor)
                .setInActiveColor(R.color.inactivecolor)
                .setFirstSelectedPosition(0)
                .setMode(BottomNavigationBar.MODE_FIXED)//设置为混合模式
                .initialise();
        //给Tab设置点击监听
        bottomNavigationBar.setTabSelectedListener(new MyTabSelectedListener());
    }

    private int mPosition;

    private class MyTabSelectedListener implements BottomNavigationBar.OnTabSelectedListener {
        //显示Fragment
        @Override
        public void onTabSelected(int position) {
            MyLogger.i("onTabSelected:" + position);
            //1.通过FragmentManager获取2.如果不存在就通过FragmentFactory创建
            //3.如果存在就显示
            mPosition = position;
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment == null) {
                //添加
                ft.add(R.id.contaier, FragmentFactory.getInstance(fragments[position]), position + "");
                if (position!=3) {
                    loading();
                }

            } else {
                //显示
                ft.show(fragment);
            }
            ft.commit();
        }

        //隐藏Fragment
        @Override
        public void onTabUnselected(int position) {
            MyLogger.i("onTabUnselected:" + position);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();
            ft.hide(fragment);
            ft.commit();
        }

        @Override
        public void onTabReselected(int position) {
            MyLogger.i("onTabReselected:" + position);
        }
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //把当前的activity移至到后台
//            //是否为任务栈的
//            moveTaskToBack(false);
//            return true;//自己处理后退键的行为
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onBackPressed() {
        BaseFragment fragment0 = (BaseFragment) getSupportFragmentManager()
                .findFragmentByTag("" + mPosition);
        if (!fragment0.onKeyBackPressed()) {//fragment.onKeyBackPressed()返回false代表未消费事件
            super.onBackPressed();//继续执行原有返回逻辑
        }
    }
    private void checkUpdate(){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://dadaappid.com/getAppConfig.php?appid=454446")
                .get()//默认就是GET请求，可以不写
                .build();
        Call call2 = okHttpClient.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("dhdhdh", "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = null;
                if (response.body() != null) {
                    result = response.body().string();
                }
                    Log.e("dhdhdh", result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    final String url = jsonObject.optString("Url");
                    String success = jsonObject.optString("success");
                    if ("true".equals(success) && !TextUtils.isEmpty(url)) {
                        boolean avilible = NetStatusUtil.isAvilible(MainActivity.this, "com.bxvip.app.dadazy");
                        if (!avilible) {
                            Intent intent = new Intent(MainActivity.this, DownLoadingActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
