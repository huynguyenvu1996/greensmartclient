package com.group07.greensmart.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.group07.greensmart.R;

/**
 * Created by huyjackson on 7/26/17.
 */

public class DialogFilter implements View.OnClickListener {

    private Dialog dialogfFilter;
    private Context context;
    private OnFilterListener onFilterListener;
    private String mFilter = "desc";


    public DialogFilter(Context context) {
        this.context = context;
    }

    public void setOnDownloadLocationSelectedListener(OnFilterListener onFilterListener) {
        this.onFilterListener = onFilterListener;
    }

    public void show() {
        dialogfFilter = new Dialog(context);
        dialogfFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogfFilter.setContentView(R.layout.dialog_filter);
        dialogfFilter.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RadioGroup radGroup = dialogfFilter.findViewById(R.id.rad_group_filter);
        RadioButton radASC = dialogfFilter.findViewById(R.id.rad_filter_asc);
        RadioButton radDESC = dialogfFilter.findViewById(R.id.rad_filter_desc);


        Button btnCancel = (Button) dialogfFilter.findViewById(R.id.btn_filter_cancel);
        Button btnConfirm = (Button) dialogfFilter.findViewById(R.id.btn_filter_ok);


        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rad_filter_asc: {
                        if (onFilterListener != null) {
                            mFilter = "asc";
                        }

                    }
                    ;
                    break;
                    case R.id.rad_filter_desc: {
                        if (onFilterListener != null) {
                            mFilter = "desc";
                        }

                    }
                    ;
                    break;
                }
            }
        });
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        dialogfFilter.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter_cancel: {
                if (dialogfFilter != null) {
                    dialogfFilter.dismiss();
                }
            }
            ;
            break;
            case R.id.btn_filter_ok: {
                if (onFilterListener != null) {
                    onFilterListener.onFilterListener(mFilter);
                }
            }
            ;
            break;
        }
    }

    public interface OnFilterListener {
        void onFilterListener(String filter);
    }


}
