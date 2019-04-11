package com.ltn.avroraflowers.ui.activities.mainActivity

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class BottomNavigationViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    //FIXME
    //lateinit var lock: Boolean

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return !lock && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return !lock && super.onInterceptTouchEvent(event)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return !lock && super.canScrollHorizontally(direction)
    }


}