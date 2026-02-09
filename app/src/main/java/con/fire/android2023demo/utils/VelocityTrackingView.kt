package con.fire.android2023demo.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import kotlin.math.sqrt

class VelocityTrackingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var velocityTracker: VelocityTracker? = null
    private var lastX = 0f
    private var lastY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val x = event.x
        val y = event.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                velocityTracker = VelocityTracker.obtain()
                velocityTracker?.addMovement(event)
                lastX = x
                lastY = y
            }

            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.apply {
                    addMovement(event)

                    // 计算速度（像素/秒）
                    computeCurrentVelocity(1000) // 参数是单位时间（毫秒）

                    val velocityX = xVelocity
                    val velocityY = yVelocity

                    Log.d("Velocity",
                        "Velocity X: $velocityX px/sec, Y: $velocityY px/sec")

                    // 计算滑动速度大小
                    val speed = sqrt(velocityX * velocityX + velocityY * velocityY)
                    Log.d("Velocity", "Speed: $speed px/sec")
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                velocityTracker?.recycle()
                velocityTracker = null
            }
        }
        return true
    }
}