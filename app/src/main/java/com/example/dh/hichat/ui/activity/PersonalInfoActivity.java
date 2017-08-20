package com.example.dh.hichat.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseActivity;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by DH on 2017/8/20.
 */

public class PersonalInfoActivity extends BaseActivity {
    private android.widget.ImageView ivicon;
    private android.widget.TextView tvnickname;
    private android.widget.TextView tvbirthday;
    private android.widget.TextView tvhome;
    private android.widget.RadioButton rbmale;
    private android.widget.RadioButton rbfemale;
    private android.widget.RadioGroup rgsex;
    private android.widget.Button btnext;
    private android.widget.LinearLayout activitypersonalinfo;
    private String mNickName;
    private DatePickerDialog mDatePickerDialog;

    @Override
    public View addContent(LayoutInflater mInflater, FrameLayout frameLayout) {
        View view = mInflater.inflate(R.layout.activity_personal_info, frameLayout, true);
        btnext = (Button) view.findViewById(R.id.bt_next);
        rgsex = (RadioGroup) view.findViewById(R.id.rg_sex);
        rbfemale = (RadioButton) view.findViewById(R.id.rb_female);
        rbmale = (RadioButton) view.findViewById(R.id.rb_male);
        tvhome = (TextView) view.findViewById(R.id.tv_home);
        tvbirthday = (TextView) view.findViewById(R.id.tv_birthday);
        tvnickname = (TextView) view.findViewById(R.id.tv_nickname);
        ivicon = (ImageView) view.findViewById(R.id.iv_icon);
        return view;
    }

    @Override
    public void initContent(View view) {
        btnext.setOnClickListener(this);
        ivicon.setOnClickListener(this);
        tvhome.setOnClickListener(this);
        tvbirthday.setOnClickListener(this);
        rbmale.setOnClickListener(this);
        rbfemale.setOnClickListener(this);

        mNickName = getIntent().getStringExtra("nickName");
        tvnickname.setText(mNickName);
    }

    public void onClick(View view) {
        super.onClick(view);//让父类的onClick()得到调用
        switch (view.getId()) {
            case R.id.iv_icon:
                pickPictureFromSystemGallery();
                break;
            case R.id.tv_home:
                showSelectHomeDialog();
                break;
            case R.id.tv_birthday:
                showSelectDateDialog();
                break;
            case R.id.rb_male:
                changeSexCheckState(true);
                break;
            case R.id.rb_female:
                changeSexCheckState(false);
                break;
            case R.id.bt_next:
                showSexConfirmDialog();
                break;
            default:

                break;
        }
    }

    private void showSexConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("注册成功后性别不可以修改")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进入注册界面
                        Intent intent=new Intent(PersonalInfoActivity.this,RegisterActivity.class);
                        PersonalInfoActivity.this.startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    //修改下一步的状态
    private void changeNextButtonState() {
        //头像
        Drawable drawable = ivicon.getDrawable();
        if (drawable == null) {
            btnext.setEnabled(false);
            return;
        }
        //生日
        String birthday = tvbirthday.getText().toString();
        if (TextUtils.isEmpty(birthday)) {
            btnext.setEnabled(false);
            return;
        }
        //家乡
        String home = tvhome.getText().toString();
        if(TextUtils.isEmpty(home)){
            btnext.setEnabled(false);
            return;
        }
        //性别
        if(!(rbmale.isChecked() || rbfemale.isChecked())){
            btnext.setEnabled(false);
            return;
        }
        btnext.setEnabled(true);
    }
    //修改性别的选择
    private void changeSexCheckState(boolean isChecked) {
        rbmale.setChecked(isChecked);
        rbfemale.setChecked(!isChecked);
        changeNextButtonState();
    }

    //弹出城市选择对话框
    private CityPicker cityPicker;

    private void showSelectHomeDialog() {
        if (cityPicker == null) {
            cityPicker=new CityPicker.Builder(this)
                    .title("选择家乡")
                    .textSize(20)
                    .titleBackgroundColor("#b9b7b8")
                    .onlyShowProvinceAndCity(true)
                    .cancelTextColor("#FF4081")
                    .confirTextColor("#FF4081")
                    .province("北京市")
                    .city("东城区")
                    .district("无")
                    .textColor(Color.parseColor("#000000"))
                    .provinceCyclic(true)
                    .cityCyclic(false)
                    .districtCyclic(false)
                    .visibleItemsCount(7)
                    .itemPadding(10)
                    .build();
            //确定选择监听
            cityPicker.setOnCityItemClickListener(new MyOnCityItemClickListener());
        }
        cityPicker.show();
    }

    private class MyOnCityItemClickListener implements CityPicker.OnCityItemClickListener {

        @Override
        public void onSelected(String... citySelected) {
            tvhome.setText(citySelected[0]+"-"+citySelected[1]);
            changeNextButtonState();
        }
    }
    //弹出选择日期的对话框
    private void showSelectDateDialog() {
        //日历对象
        Calendar calendar=Calendar.getInstance();
        //日期选择对话框

        if(mDatePickerDialog==null) {
            mDatePickerDialog = DatePickerDialog.newInstance(new MyOnDateSetListener(),//日期选择监听
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    false);//是否振动
            mDatePickerDialog.setYearRange(1985, 2028);
        }
        mDatePickerDialog.show(getSupportFragmentManager(),"DATEPICKER_TAG");
    }

    private class MyOnDateSetListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
            //把选择的日期显示在TextView里
            tvbirthday.setText(year+"-"+month+"-"+day);
            changeNextButtonState();
        }
    }
    //从系统的相册中获取一张图片
    private void pickPictureFromSystemGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                //从系统相册返回的
                if (null != data) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        //调用系统的图片裁剪
                        crop(uri);
                    }
                }
                break;
            case 200:
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    //显示图片
                    ivicon.setImageBitmap(bitmap);
                    changeNextButtonState();
                    try {
                        //保存图片(/data/packageName/files
                        FileOutputStream stream = openFileOutput(mNickName + ".jpg", Context.MODE_PRIVATE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:

                break;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri 图片的路径
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 200);
    }
}
