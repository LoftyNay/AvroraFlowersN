package com.ltn.avroraflowers.ui.activities.mainActivity

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class BottomNavigationViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var enablePaging: Boolean = true
    private var enableSmoothScroll: Boolean = true

    fun setEnablePaging(enable: Boolean) {
        enablePaging = enable
    }

    fun setEnableSmoothScroll(enable: Boolean) {
        enableSmoothScroll = enable
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, enableSmoothScroll)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return enablePaging && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return enablePaging && super.onInterceptTouchEvent(event)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return enablePaging && super.canScrollHorizontally(direction)
    }
}