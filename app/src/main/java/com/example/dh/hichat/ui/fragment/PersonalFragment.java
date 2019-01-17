package com.example.dh.hichat.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;
import com.example.dh.hichat.ui.activity.MainActivity;
import com.example.dh.hichat.weight.IdeaDialog;
import com.example.dh.hichat.weight.TipDialog;

import java.util.Objects;

/**
 * Created by DH on 2017/8/10.
 */

public class PersonalFragment extends BaseFragment {
    ImageView mIvIcon;
    LinearLayout mDaijiaLin;
    LinearLayout mSettingLin;
    LinearLayout mVersionLin;

    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("关于彩神争霸");
    }

    @Override
    public void showBodyContent() {
        super.showBodyContent();
        View view = View.inflate(getContext(), R.layout.fragment_personal, null);
        mDaijiaLin = view.findViewById(R.id.daijiaLin);
        mSettingLin = view.findViewById(R.id.settingLin);
        mVersionLin = view.findViewById(R.id.versionLin);
        content.addView(view);
        mDaijiaLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg="【免责声明】\n" +
                        "      本app旨在为用户提供开奖信息和类型介绍，方便用户时间查阅开奖信息，暂不支持购买，请放心使用          \n" +
                        "1、彩票市场存在风险，请谨慎投资！   \n" +
                        "2、公益为主、娱乐其次、中奖第三！   \n" +
                        "3、福彩宗旨:扶老、助残、济困、救孤！\n" +
                        "4、体彩精神:责任、诚信、团结、创新！\n" +
                        "5、本软件所有功能仅为投资辅助工具，由于软件等原因不能保证所有功能的完全正确性，不提供任何投资建议，所有风险由投资者个人承担，软件开发商不承担任何。";
                ((MainActivity) Objects.requireNonNull(getActivity())).showDlg(msg);
            }
        });
        mSettingLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdeaDialog dialog = new IdeaDialog(getActivity());
                dialog.setType(TipDialog.TYPE_ONE_BUTTON);
                dialog.setListener(new IdeaDialog.TipDialogClickListener() {
                    @Override
                    public void clickCancel() {

                    }

                    @Override
                    public void clickSure() {
                        ((MainActivity) Objects.requireNonNull(getActivity())).loading();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActivity) Objects.requireNonNull(getActivity())).dismissLoading();
                                Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
                            }
                        },1000);
                    }
                });
                dialog.show();
            }
        });
        mVersionLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) Objects.requireNonNull(getActivity())).loading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity) Objects.requireNonNull(getActivity())).dismissLoading();
                        Toast.makeText(getActivity(),"已是最新版本，无需更新",Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }
        });
    }



}
