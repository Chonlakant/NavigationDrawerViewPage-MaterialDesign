package br.liveo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class NotifyingScrollView2 extends ScrollView {


    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public NotifyingScrollView2(Context context) {
        super(context);
    }

    public NotifyingScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.mOnScrollChangedListener = listener;
    }

}