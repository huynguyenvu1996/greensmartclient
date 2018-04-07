package com.group07.greensmart.listener;

import android.view.View;

/**
 * Created by nguyenvuhuy on 4/7/18.
 */

public interface RecycleViewOnItemClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
