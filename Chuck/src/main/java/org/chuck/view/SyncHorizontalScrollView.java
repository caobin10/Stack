package org.chuck.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class SyncHorizontalScrollView extends HorizontalScrollView {
    private int scrollX;
    public SyncHorizontalScrollView(Context context) {
        super(context);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void animOnScroll(int scrollX){
        this.scrollX=scrollX;
        this.post(scrollAction);
    }

    private Runnable scrollAction = new Runnable() {

        @Override
        public void run() {
            smoothScrollTo(scrollX, 0);
        }
    };
}
