package con.fire.android2023demo.ui

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import con.fire.android2023demo.R
import kotlin.math.sqrt

class TouchActivity : AppCompatActivity() {

    private var velocityTracker: VelocityTracker? = null
    private lateinit var tvPressure: TextView
    private lateinit var tvSpeed: TextView
    private var maxPressure = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)

        tvPressure = findViewById(R.id.tv_pressure)
        tvSpeed = findViewById(R.id.tv_speed)

        val touchView = findViewById<View>(R.id.touch_view)

        touchView.setOnTouchListener { _, event ->
            processTouchEvent(event)
            true
        }
    }

    private fun processTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                velocityTracker = VelocityTracker.obtain()
                velocityTracker?.addMovement(event)
                maxPressure = event.getPressure()
                updateDisplay(maxPressure, 0f)
            }

            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.addMovement(event)
                velocityTracker?.computeCurrentVelocity(1000)

                val pressure = event.getPressure()
                val speed = calculateSpeed(velocityTracker)

                if (pressure > maxPressure) maxPressure = pressure

                updateDisplay(pressure, speed)
            }

            MotionEvent.ACTION_UP -> {
                velocityTracker?.computeCurrentVelocity(1000)
                val finalSpeed = calculateSpeed(velocityTracker)

                Log.d("TouchStats",
                    "最大压力: $maxPressure, 最终速度: $finalSpeed")

                velocityTracker?.recycle()
                velocityTracker = null

                updateDisplay(0f, 0f)
            }
        }
    }

    private fun calculateSpeed(tracker: VelocityTracker?): Float {
        return if (tracker != null) {
            val x = tracker.xVelocity
            val y = tracker.yVelocity
            sqrt(x * x + y * y)
        } else {
            0f
        }
    }

    private fun updateDisplay(pressure: Float, speed: Float) {
        tvPressure.text = "压力: ${String.format("%.2f", pressure)}"
        tvSpeed.text = "速度: ${String.format("%.2f", speed)} px/秒"
    }
}