package com.example.dh.hichat.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dh.hichat.R;


/**
 */
public class IdeaDialog extends Dialog implements View.OnClickListener {
    public static final int TYPE_ONE_BUTTON = 0x100001;
    public static final int TYPE_TWO_BUTTON = 0x100002;
    private Context context;
    private EditText msgTv;
    private Button cancelBtn;
    private Button sureBtn;
    private View line;
    /*控制按钮数量 TYPE_ONE_BUTTON一个按钮，TYPE_TWO_BUTTON两个按钮*/
    private int type = TYPE_ONE_BUTTON;
    private TipDialogClickListener listener;

    public IdeaDialog(Context context) {
        super(context, R.style.Translucent_NoTitle);
        initView(context);
    }

    public IdeaDialog(Context context, int theme) {
        super(context, R.style.Translucent_NoTitle);
        initView(context);
    }

    protected IdeaDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public void setLBtnText(String text) {
        cancelBtn.setText(text);
    }

    public void setRBtnText(String text) {
        sureBtn.setText(text);
    }

    private void initView(Context context) {
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_dialog_idea);
        msgTv = (EditText) findViewById(R.id.msgTv);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        sureBtn = (Button) findViewById(R.id.sureBtn);
        line = findViewById(R.id.line);

        cancelBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        Activity activity = (Activity) context;
        WindowManager wm = activity.getWindowManager();
        Display display = wm.getDefaultDisplay();
        lp.width = display.getWidth() * 5 / 6; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (type == TYPE_ONE_BUTTON) {
                        return true;
                    } else {
                        if (listener != null) {
                            listener.clickCancel();
                        }
                    }
                    return false;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });
    }

    public void setListener(TipDialogClickListener listener) {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
        if (type == TYPE_ONE_BUTTON) {
            cancelBtn.setVisibility(View.GONE);
            sureBtn.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
        } else if (type == TYPE_TWO_BUTTON) {
            cancelBtn.setVisibility(View.VISIBLE);
            sureBtn.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelBtn:
                dismiss();
                if (listener != null) {
                    listener.clickCancel();
                }
                break;

            case R.id.sureBtn:
                dismiss();
                if (listener != null) {
                    listener.clickSure();
                }
                break;
            default:
                break;
        }
    }

    public interface TipDialogClickListener {
        void clickCancel();

        void clickSure();
    }
}
