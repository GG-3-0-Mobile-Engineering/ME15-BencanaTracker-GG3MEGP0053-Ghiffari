package com.example.bencanatracker

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.AutoCompleteTextView

class ExtendedAutoCompleteTextView : AutoCompleteTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // Add missing methods for your custom view
    override fun getDropDownHeight(): Int {
        return if (isPopupShowing) {
            val popupHeight = super.getDropDownHeight()
            val availableHeight = getAvailableHeight()
            if (popupHeight > availableHeight) {
                availableHeight
            } else {
                popupHeight
            }
        } else {
            0
        }
    }

    override fun showDropDown() {
        super.showDropDown()
    }

    override fun dismissDropDown() {
        super.dismissDropDown()
    }

    private fun getAvailableHeight(): Int {
        val r = Rect()
        getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top - paddingTop - paddingBottom
    }
}
