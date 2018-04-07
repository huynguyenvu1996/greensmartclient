package com.group07.greensmart.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.group07.greensmart.R;
import com.group07.greensmart.utils.HoverMotionUtils;


/**
 * Created by huyjackson on 7/22/17.
 */

public class DialogAboutApps {

    private Context context;
    private Button btnOk;
    private Dialog dialog;
    private ImageView imgAbout;
    private HoverMotionUtils hoverMotionUtils;


    public DialogAboutApps(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about_application);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        imgAbout = dialog.findViewById(R.id.img_about_apps);
        btnOk = (Button) dialog.findViewById(R.id.btn_about_ok);

        hoverMotionUtils = new HoverMotionUtils();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    hoverMotionUtils.stop();
                    dialog.dismiss();
                }
            }
        });
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.show();
            hoverMotionUtils.start(imgAbout);
        }
    }
}
