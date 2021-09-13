package com.example.myappui

import android.content.Context
import android.transition.Fade
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout

class SavedArea @JvmOverloads constructor(
    context:Context, attrs: AttributeSet?=null,
    defStyleAttr: Int=0) :GridLayout(context,attrs,defStyleAttr) {
    var listener:((view:View)->Unit)?=null
    override fun addView(child:View?){
        child?.setOnLongClickListener{
            listener?.invoke(child)
            false
        }
        animate(Fade.MODE_IN)
        super.addView(child)
    }

    private fun animate(modeIn: Int) {

    }

    override fun removeView(view:View?){
        animate(Fade.MODE_OUT)
        super.addView(view)
    }
}