package com.example.dh.hichat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.dh.hichat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DownLoadingActivity extends AppCompatActivity {

    private TextView mTxtHost;
    private EditText mEdtUrl;
    private Button mBtnSreach;

    private String mUrl;
    private TextView tv;
    private NumberProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_loading);

//        tv = (TextView) findViewById(R.id.tv);
        pb = (NumberProgressBar) findViewById(R.id.pb);
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            update(url);
        }
//        initViews();
//        initEvents();

    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        // Android use X509 cert
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean update(final String urlStr) {
        handler = new UpdateHandler();
        final File file = new File(Environment.getExternalStorageDirectory() + "/" + "caishen.apk");
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection connection;
                    //关键代码
                    //ignore https certificate validation |忽略 https 证书验证
                    if (url.getProtocol().toUpperCase().equals("HTTPS")) {
                        trustAllHosts();
                        HttpsURLConnection https = (HttpsURLConnection) url
                                .openConnection();
                        https.setHostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
                        connection = https;
                    } else {
                        connection = (HttpURLConnection) url.openConnection();
                    }
//                    connection.setSSLSocketFactory(context.getSocketFactory());

                    size = connection.getContentLength();
                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    connection.connect();

                    if (connection.getResponseCode() >= 400) {
//                        showToast("下载失败");

                    } else {
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                            hasRead += len;
                            index = (int) (hasRead * 100) / size;
                            message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            Log.e("dhdhdh", index+"");
                        }

                        inputStream.close();
                        outputStream.close();

                        openFile(file);

//                        dismiss();
                    }
                } catch (Exception e) {
                    flag = false;
                    e.printStackTrace();
                }
            }
        }).start();

        return flag;
    }
    private View view;
    private Context context;
    private TextView messageTv;
    private Button leftBtn;
    private Button rightBtn;
    private LinearLayout twoBtnLin;
    private ProgressBar progressBar;
    private String downloadUrl;
    private boolean isMustUpdate;
    /*是否正在打开安装包*/
    private boolean isOpenFile = false;
    private Message message = null;
    private boolean flag = true;
    private int size = 1;
    private int hasRead = 0;
    private int len = 0;
    private byte buffer[] = new byte[256];
    private volatile int index = 0;
    private UpdateHandler handler;


    /**
     * 打开APK程序代码
     *
     * @param file
     */
    private void openFile(File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.bxvip.app.dadazy.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        finish();
        isOpenFile = true;
    }
    class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
//                progressBar.setProgress(index);
//                tv.setText(index+"/100");
                pb.setProgress(index);
            }

            super.handleMessage(msg);
        }

    }
}
