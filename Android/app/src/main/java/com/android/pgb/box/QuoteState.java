package com.android.pgb.box;

/**
 * Created by softsea on 17/8/1.
 */

public enum QuoteState {

    XJStop(-2),
    XJNotPay(-1),
    XJIng(0),
    XJEnd(1),
    XJFinish(2),
    XJOverTime(3);
    public int value;

    QuoteState(int i) {

        this.value = i;
    }
}
