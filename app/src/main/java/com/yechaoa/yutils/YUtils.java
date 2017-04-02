package com.yechaoa.yutils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yechao on 2017/4/2.
 * Describe : 快速开发工具集合
 *
 * GitHub : https://github.com/bige-ye
 * CSDN : http://blog.csdn.net/yechaoa
 *
 */

public class YUtils {

    private static Activity activity;
    public static SharedPreferences sp;
    public static final String FILE_NAME = "config";
    private static Toast toast;
    private static ProgressDialog progressDialog;

    /**
     * @param act 设置当前Activity
     */
    public static void setActivity(Activity act) {
        activity = act;
        sp = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @return 获取当前Activity
     */
    public static Activity getActivity() {
        return activity;
    }


    /**
     * String操作
     * @param key
     * @param value
     */
    public static void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return sp.getString(key, "");
    }

    /**
     * int操作
     * @param key
     * @param value
     */
    public static void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }

    /**
     * Boolean操作
     * @param key
     * @param value
     */
    public static void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * 根据key值移除value
     * @param key
     */
    public static void removeString(String key) {
        sp.edit().remove(key).commit();
    }

    /**
     * 移除所有
     * @param key
     */
    public static void removeAll(String key) {
        sp.edit().clear().commit();
    }


    /**
     * Log 打印  不带Tag参数的
     * @param msg 打印参数
     */
    public static void i(String msg) {
        Log.i("Tag", "----------"+msg);
    }

    public static void e(String msg) {
        Log.e("Tag", "----------"+msg);
    }


    /**
     * Log 打印  带Tag参数的
     * @param tag 打印标记
     * @param msg 打印参数
     */
    public static void i(String tag,String msg) {
        Log.i(tag, "----------"+msg);
    }

    public static void e(String tag,String msg) {
        Log.e(tag, "----------"+msg);
    }


    /**
     * @return 获取屏幕宽度
     */
    public static int getScreenWidth(){
        return YUtils.getActivity().getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * @return 获取屏幕高度
     */
    public static int getScreenHeight(){
        return YUtils.getActivity().getWindowManager().getDefaultDisplay().getHeight();
    }


    /**
     * Loading加载框
     * @param msg 显示的参数
     */
    public static void showLoading(String msg){
        progressDialog = ProgressDialog.show(YUtils.getActivity(), "",msg, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * dismissLoading
     */
    public static void dismissLoading(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


    /**
     * 根据时间休眠然后关闭当前页面
     * 比如：5秒自动返回
     * 或者只需要后台给一个结果而已
     * @param millis 时长
     */
    public static void finishBySleep(final long millis){
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    YUtils.getActivity().finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * @return 获取版本名
     */
    public static String getVersionName(){
        try {
            PackageManager packageManager = YUtils.getActivity().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(YUtils.getActivity().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return 获取版本号
     */
    public static int getVersionCode(){
        try {
            PackageManager packageManager = YUtils.getActivity().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(YUtils.getActivity().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 检验手机号
     * @param mobiles
     * @return
     */
    public static boolean checkPhoneNumber(String mobiles) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(mobiles);
        b = m.matches();
        return b;
    }


    /**
     * MD5加密
     * @param data
     * @return
     */
    public static String MD5(String data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(data.getBytes());
        byte[] m = md5.digest();
        return Base64.encodeToString(m, Base64.DEFAULT);
    }


    /**
     * dp2px
     * @param dp
     * @return
     */
    public static int dp2px(float dp){
        float density = YUtils.getActivity().getResources().getDisplayMetrics().density;
        return (int)(dp * density+0.5f);
    }

    /**
     * px2dp
     * @param px
     * @return
     */
    public static float px2dp(int px){
        float density = YUtils.getActivity().getResources().getDisplayMetrics().density;
        return px/density;
    }


    /**
     * 复制文本到粘贴板
     * @param text 需要复制的参数
     */
    public static void copyToClipboard(String text){
        if(Build.VERSION.SDK_INT >= 11){
            ClipboardManager cbm = (ClipboardManager) YUtils.getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setPrimaryClip(ClipData.newPlainText(YUtils.getActivity().getPackageName(), text));
        }else {
            android.text.ClipboardManager cbm = (android.text.ClipboardManager) YUtils.getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setText(text);
        }
    }


    /**
     * @return 判断网络状态
     */
    public static boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) YUtils.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * showCenterToast 居中显示
     * @param msg 需要显示的参数
     */
    public static void showCenterToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createCenterToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createCenterToast(msg);
                }
            });
        }
    }

    /**
     * createCenterToast
     * @param msg 接收参数
     */
    private static void createCenterToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * showTopToast 顶部显示
     * @param msg 需要显示的参数
     */
    public static void showTopToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createTopToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createTopToast(msg);
                }
            });
        }
    }

    /**
     * createTopToast
     * @param msg 接收参数
     */
    private static void createTopToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        toast.setGravity(Gravity.TOP, 0, getScreenWidth()/5);
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * showBottomToast 底部显示
     * @param msg 需要显示的参数
     */
    public static void showBottomToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createBottomToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createBottomToast(msg);
                }
            });
        }
    }

    /**
     * createBottomToast
     * @param msg 接收参数
     */
    private static void createBottomToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        //toast.setGravity(Gravity.BOTTOM, 0, 0);使用默认位置
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * showCenterLongToast
     * @param msg 需要显示的参数
     */
    public static void showCenterLongToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createCenterLongToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createCenterLongToast(msg);
                }
            });
        }
    }

    /**
     * createCenterLongToast
     * @param msg 接收参数
     */
    private static void createCenterLongToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * showTopLongToast
     * @param msg 需要显示的参数
     */
    public static void showTopLongToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createTopLongToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createTopLongToast(msg);
                }
            });
        }
    }

    /**
     * createTopLongToast
     * @param msg 接收参数
     */
    private static void createTopLongToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        toast.setGravity(Gravity.TOP, 0, getScreenWidth()/5);
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * showBottomLongToast
     * @param msg 需要显示的参数
     */
    public static void showBottomLongToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createBottomLongToast(msg);
        } else {
            YUtils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createBottomLongToast(msg);
                }
            });
        }
    }

    /**
     * createBottomLongToast
     * @param msg 接收参数
     */
    private static void createBottomLongToast(String msg) {
        if(toast==null){
            toast = Toast.makeText(YUtils.getActivity(), msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        //toast.setGravity(Gravity.BOTTOM, 0, 0);使用默认位置
        messageTextView.setTextSize(15);
        toast.show();
    }

    /**
     * 取消Toast
     * onDestroy时调用，或者onPause
     * 当前页面finish之后在下一个页面不会显示
     */
    public static void cancelToast() {
        if(toast!=null){
            toast.cancel();
        }
    }


}