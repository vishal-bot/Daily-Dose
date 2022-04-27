package com.vishal.Dailydose.ModelClass;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import com.vishal.Dailydose.PaymentActivity;
import com.vishal.Dailydose.Utils;

public class Payment {
    private Context ctx;
    public Payment(Context context){
        this.ctx = context;
    }
    @JavascriptInterface
    public void getResult(String rslt){
        if(rslt.equals("success")){
            PaymentActivity.placeOrder(ctx);
        } else {
            PaymentActivity.closeScreen((Activity) ctx);
        }
        Utils.showToast(ctx,rslt);
    }
}
