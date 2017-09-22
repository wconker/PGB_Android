package com.android.pgb.base;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by softsea on 17/8/8.
 */

public abstract class BaseFragment extends Fragment {

    protected PullToRefreshListView pullListView;

    protected void firstLoad() {

        new CountDownTimer(300, 300) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                pullListView.setRefreshing(true);
            }
        }.start();
    }

}
