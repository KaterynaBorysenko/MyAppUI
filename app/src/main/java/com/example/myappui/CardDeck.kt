package com.example.myappui

import android.content.Context

import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.transition.Fade
import java.time.format.DecimalStyle
import kotlin.math.abs


private const val CARDS_SWIPE_LENGTH=190f

class CardDeck @JvmOverloads constructor(context: Context,attrs: AttributeSet?=null,
                                         defStyleAttr:Int=0):ViewGroup(context,attrs,defStyleAttr)  {

    private var xDisplacement=12
    private var yDisplacement=12
    private var zDisplacement=3
    private var fMaxCardsNum=10
    var maxCardsNum : Int get() = fMaxCardsNum
    private var h_padding=fMaxCardsNum*xDisplacement
    private var v_padding=fMaxCardsNum*yDisplacement
    var  onSwipeRightListener:((view:View)->Unit)?=null
    var onSwipeLeftListener: ((view:View)->Unit)?=null
    private var pX=0f
    private var dX=0f
    private var pY=0f
    private var dY=0f

    init {
        val prtArray=context.obtainStyledAttributes(attrs,R.styleable.CardDeck,defStyleAttr,0)
        xDisplacement=prtArray.getInteger(R.styleable.CardDeck_xDisplacement,12)
        yDisplacement=prtArray.getInteger(R.styleable.CardDeck_yDisplacement,12)
        zDisplacement=prtArray.getInteger(R.styleable.CardDeck_zDisplacement,3)
        maxCardsNum=prtArray.getInteger(R.styleable.CardDeck_maxCardsNum,10)
        prtArray.recycle()
    }
    override fun addView(child:View?){
        animate(Fade.MODE_IN)
        super.addView(child)
    }

    private fun animate(modeIn: Int) {
        TODO("Not yet implemented")
    }

    override fun removeView(view: View?){
        animate(Fade.MODE_OUT)
        super.removeView(view)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width_mode=MeasureSpec.getMode(widthMeasureSpec)
        val width_size=MeasureSpec.getSize(widthMeasureSpec)
        val height_mode=MeasureSpec.getMode(heightMeasureSpec)
        for (child in children){
            measureChild(child,MeasureSpec.makeMeasureSpec(width_size
                    -h_padding-paddingStart-paddingEnd,width_mode),MeasureSpec.makeMeasureSpec(
                (width_size*2.5).toInt()
                        -v_padding-paddingTop-paddingBottom,height_mode
            ))
        }
        setMeasuredDimension(width_size,
            (2.5*width_size).toInt())
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var isdX=-xDisplacement*childCount/2+xDisplacement/2
        var isdY=yDisplacement*childCount/2+yDisplacement/2
        var isdZ=zDisplacement.toFloat()

        for (child in children){
            child.elevation=isdZ
            child.layout(h_padding/2-isdX,v_padding/2-isdY,h_padding/2+child.measuredWidth-isdX,
                v_padding/2+child.measuredHeight-isdY)
            isdX+=xDisplacement
            isdY+=yDisplacement
            isdZ+=zDisplacement
        }
    }
    private fun swipeTop(){
        val view=children.last()
        removeView(view)
        addView(view,0)
    }
    private fun swipeBottom(){
        val view=children.first()
        removeView(view)
        addView(view)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(childCount==0)return false
        when(event?.action){
            MotionEvent.ACTION_UP->{
                dX=event.rawX-pX
                dY=event.rawY-pY
                children.last().translationX=0f
                children.last().translationY=0f
                if (abs(dY)> CARDS_SWIPE_LENGTH){
                    if (dY>0){
                        swipeBottom()
                    } else{
                        swipeTop()
                    }
                }
                if (abs(dX)> CARDS_SWIPE_LENGTH){
                    if (dX>0){
                        onSwipeRightListener?.invoke(children.last())
                    }else{
                        onSwipeLeftListener?.invoke(children.last())
                    }
                }
                pX=event.rawX
                pY=event.rawY
            }
            MotionEvent.ACTION_DOWN->{
                pX=event.rawX
                pY=event.rawY
            }
            MotionEvent.ACTION_MOVE ->{
                dX=event.rawX-pX
                dY=event.rawY-pY
                children.last().translationX=dX
                children.last().translationY=dY
            }
        }
        return true
    }
}


