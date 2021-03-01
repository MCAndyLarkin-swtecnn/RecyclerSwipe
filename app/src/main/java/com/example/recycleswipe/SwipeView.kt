package com.example.recycleswipe

import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.Exception
import java.lang.Integer.max
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.min
import kotlin.math.round


class SwipeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    enum class Screen {
        Front,
        Left,
        Right,
        Call,
        Message
    }


    var textBrush = Paint()

    val SCREEN_WIGHT = 1440
    var MEMBER_HEIGHT = 250                                                                         //Other dimension
    val SCREEN_HEIGHT = 3040
    var nameSurname = "Anonim"
    var phonenNumber = "NO number"

    var photo = R.drawable.jesus
    var lastCall = "No last Calls"
    var operator = "Unknown oper"

    var attrs: AttributeSet = attrs!!
    var _context: Context = context!!

    data class ConfigSet(
        var bitmap: Bitmap?,
        var wight: Int,
        var brush: Paint,
        var rect: Rect?
    )
    var configMap = HashMap<Screen, ConfigSet>()
    var wallWight: Int = 0
    fun updatePhoto(photo: Int) {
        this.photo = photo
        val rightbrush = Paint ()
        rightbrush.color = Color.WHITE
        val _matrix = Matrix()
        _matrix.postScale(.15F, .15f)
//        var drawable = Drawable.createFromResourceStream(resources,photo)                         //Need get weigh and heigh
        val bitFac = BitmapFactory.decodeResource(resources, photo)
        configMap[Screen.Right] = ConfigSet(
        Bitmap.createBitmap(
                bitFac, 0,
                0, bitFac.width,  bitFac.height, _matrix, true
        ),
        SCREEN_WIGHT,
        rightbrush,
        null
        )
    }
    init {
        val frontrect = Rect()
        frontrect.set(
            0,0,
            SCREEN_WIGHT,MEMBER_HEIGHT
        )
        val frontbrush = Paint()
        frontbrush.color = Color.WHITE
        configMap[Screen.Front] = ConfigSet(
            null,
            SCREEN_WIGHT,
            frontbrush,
            frontrect
        )

        val leftrect = Rect()
        leftrect.set(
            0,0,
            0,MEMBER_HEIGHT
        )
        val leftbrush = Paint()
        leftbrush.color = resources.getColor(R.color.custom_green)
        configMap[Screen.Left] = ConfigSet(
            null,
            2*MEMBER_HEIGHT,
            leftbrush,
            leftrect
        )


        val rightbrush = Paint()
        rightbrush.color = Color.WHITE
        val _matrix = Matrix()
        _matrix.postScale(.1F, .1f)
        val bitFac = BitmapFactory.decodeResource(resources, photo)
        configMap[Screen.Right] = ConfigSet(
                Bitmap.createBitmap(
                        bitFac, 0,
                        0, bitFac.width,  bitFac.height, _matrix, true
                ),
                SCREEN_WIGHT,
                rightbrush,
                null
        )


        val callbrush = Paint()
        callbrush.color = Color.WHITE
        val callMatrix = Matrix()
        callMatrix.postScale(0.1F,.1f)


        configMap[Screen.Call] = ConfigSet(
                Bitmap.createBitmap(
                        BitmapFactory.decodeResource(resources, R.drawable.calls),
                        0,0,MEMBER_HEIGHT*7, MEMBER_HEIGHT*7,callMatrix, true
                ),
                -2*MEMBER_HEIGHT+30,
                callbrush,
                null
        )

        val messagebrush = Paint()
        messagebrush.color = Color.YELLOW
        val messageMatrix = Matrix()
        messageMatrix.postScale(0.07F,.07f)


        configMap[Screen.Message] = ConfigSet(
                Bitmap.createBitmap(
                        BitmapFactory.decodeResource(resources, R.drawable.message),
                        0,0,MEMBER_HEIGHT*9, MEMBER_HEIGHT*9,messageMatrix, true
                ),
                -2*MEMBER_HEIGHT+MEMBER_HEIGHT+20,
                messagebrush,
                null
        )
    }

    private var current: Screen = Screen.Front
    var delta: Int = 0
    var lastX: Int? = null


    override fun onTouchEvent(event: MotionEvent): Boolean {
        //If crash will be, may need custom event as Nullable
        val touch = round(event.x).toInt()
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                lastX = null
                rebase()
            }
            MotionEvent.ACTION_DOWN -> {
                lastX = touch
            }
            MotionEvent.ACTION_MOVE -> {
//                if touch
                lastX?:return false
                delta = lastX!!-touch
                invalidate()
            }
        }
        return true
    }


    private fun rebase() {                                                                          //Animate
        Log.d("Coords", "x=$x, y=$y")
        if(abs(delta) > SCREEN_WIGHT/6) {
            current = if (delta < 0) {
                when (current) {
                    Screen.Left -> Screen.Left
                    Screen.Right -> Screen.Front
                    Screen.Front -> Screen.Left
                    else -> {
                        throw Exception("EXCEPT")
                    }
                }
            } else {
                when (current) {
                    Screen.Left -> Screen.Front
                    Screen.Right -> Screen.Right
                    Screen.Front -> Screen.Right
                    else -> {
                        throw Exception("EXCEPT")
                    }
                }
            }
        }
        delta = 0
        Log.e("Current", "${current.name}, delta = $delta, lastX = $lastX")
        invalidate()
    }

    fun hide() {
        if(current == Screen.Front) return
        val valAn = ValueAnimator()
        val current_delt = configMap[current]!!.wight * if(current == Screen.Right)1 else -1
        valAn.setObjectValues(current_delt, 0)
        valAn.setEvaluator(IntEvaluator())
        valAn.addUpdateListener { p ->
            delta = p.animatedValue as Int;
            invalidate()
        }
        current = Screen.Front
        valAn.duration=1000
        valAn.start()

    }
    private fun drawNameAndNumber(canvas: Canvas){
        textBrush.textSize = 72F
        textBrush.color = Color.BLACK
        canvas.drawText(
            nameSurname,
            580f,
            110f,
            textBrush
        )
        textBrush.textSize = 64F
        textBrush.color = Color.GRAY
        canvas.drawText(
            phonenNumber,
            580f,
            210f,
            textBrush
        )
    }
    private fun drawLastAndOperator(canvas: Canvas){
        textBrush.textSize = 54F
        textBrush.color = Color.LTGRAY
        val MAX_LEN = 14
        if(lastCall.length > MAX_LEN) lastCall = lastCall.substring(0,MAX_LEN-1)+"..."
        canvas.drawText(
            lastCall,
            70f,
            220f,
            textBrush
        )
        if(operator.length > MAX_LEN) operator = operator.substring(0,MAX_LEN-1)+"..."
        canvas.drawText(
            operator,
            70f,
            120f,
            textBrush
        )
    }
    private fun drawBack(canvas: Canvas) {
        configMap[Screen.Front]?.let {
            canvas.drawRect(it.rect!!, it.brush)
        }
        textBrush.color = Color.LTGRAY
        textBrush.strokeWidth = 6f
        canvas.drawLine(220f, 0f,1220f,0f,textBrush)

    }
    private fun drawNext(canvas: Canvas) {
        val wallWight: Int
        when(current){
            Screen.Front -> {
                if (delta < 0) {//CHECK for null // move to right
                    configMap[Screen.Left]?.let {
                        it.rect!!.right=
                            min(abs(delta), configMap[Screen.Left]!!.wight)

                        canvas.drawRect(
                            it.rect!!, it.brush
                        )

                        configMap[Screen.Call]?.let {
                            canvas.drawBitmap(
                                    it.bitmap!!,
                                    it.wight.toFloat()+min(abs(delta), 2*MEMBER_HEIGHT), 30f, it.brush
                            )
                        }
                        configMap[Screen.Message]?.let {
                            canvas.drawBitmap(
                                    it.bitmap!!,
                                    it.wight.toFloat()+min(abs(delta), 2*MEMBER_HEIGHT), 40f, it.brush
                            )
                        }
                    }
                } else {// move to left
                    wallWight = min(abs(delta), MEMBER_HEIGHT)
                    configMap[Screen.Right]?.let {
                        canvas.drawBitmap(
                            it.bitmap!!,
                            SCREEN_WIGHT - wallWight.toFloat(),
                            0f,
                            it.brush
                        )
                    }
                }
            }
            Screen.Left -> {
                configMap[current]?.let {
                    it.rect!!.right = configMap[current]!!.wight - max(delta, 0)
                    canvas.drawRect(
                        it.rect!!, it.brush
                    )
                }

                configMap[Screen.Call]?.let {
                    canvas.drawBitmap(
                            it.bitmap!!,
                            30f-min(max(delta, 0), MEMBER_HEIGHT), 30f, it.brush
                    )
                }
                configMap[Screen.Message]?.let {
                    canvas.drawBitmap(
                            it.bitmap!!,
                            -min(max(delta, 0), 2*MEMBER_HEIGHT)+MEMBER_HEIGHT+20f, 40f, it.brush
                    )
                }
            }
            Screen.Right -> {
                Log.e("Draw", "$nameSurname")
                configMap[current]?.let {
                    canvas.drawBitmap(
                        it.bitmap!!,
                        SCREEN_WIGHT-MEMBER_HEIGHT.toFloat()-min(min(0,delta), MEMBER_HEIGHT),
                        0f,
                        it.brush
                    )
                }
            }
        }
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let{
            drawBack(canvas)
            drawLastAndOperator(canvas)}
            drawNameAndNumber(canvas!!)
            drawNext(canvas)
    }
}


