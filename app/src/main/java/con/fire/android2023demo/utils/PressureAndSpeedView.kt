package con.fire.android2023demo.utils
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View

class PressureAndSpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 速度追踪器
    private var velocityTracker: VelocityTracker? = null
    // 滑动速度的计算单位（1000 = 像素/秒，1 = 像素/毫秒）
    private val VELOCITY_UNIT = 1000

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val pointerId = event.getPointerId(event.actionIndex)

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // 手指按下时，初始化速度追踪器
                velocityTracker = VelocityTracker.obtain()
                velocityTracker?.addMovement(event)

                // 获取按下时的压力值
                val pressure = event.pressure
                Log.d("TouchInfo", "按下压力值: $pressure")
            }

            MotionEvent.ACTION_MOVE -> {
                // 手指滑动时，持续添加触摸事件到速度追踪器
                velocityTracker?.addMovement(event)
                // 计算速度（每1000毫秒，即每秒的像素数）
                velocityTracker?.computeCurrentVelocity(VELOCITY_UNIT)

                // 获取滑动速度（X/Y轴）
                val speedX = velocityTracker?.getXVelocity(pointerId) ?: 0f
                val speedY = velocityTracker?.getYVelocity(pointerId) ?: 0f
                // 获取滑动时的实时压力值
                val pressure = event.pressure

                Log.d("TouchInfo", "滑动压力值: $pressure")
                Log.d("TouchInfo", "X轴滑动速度: ${speedX}px/s，Y轴滑动速度: ${speedY}px/s")
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 手指抬起/取消触摸时，回收速度追踪器
                velocityTracker?.recycle()
                velocityTracker = null

                val pressure = event.pressure
                Log.d("TouchInfo", "抬起压力值: $pressure")
            }
        }
        // 返回true表示消费该触摸事件
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 可在此绘制UI，比如实时显示压力值和速度
    }
}