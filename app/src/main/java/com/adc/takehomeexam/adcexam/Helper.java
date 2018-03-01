package com.adc.takehomeexam.adcexam;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Mark Ferdie Catabona on 27/02/2018.
 */

public class Helper {

    public static ProgressDialog buildSpinnerDialog(Context context) {
        ProgressDialog  progressDialog = new ProgressDialog(context,R.style.ProgressSpinnerTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        return progressDialog;
    }
}
