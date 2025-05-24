package security
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.FrameLayout
import android.view.Gravity

class TamperDetectedActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fullscreen red background
        val root = FrameLayout(this).apply {
            setBackgroundColor(Color.RED)

            val warningText = TextView(this@TamperDetectedActivity).apply {
                val message = "⚠️ Application Tampered!\nThis app has been modified or is not genuine."
                text = message
                setTextColor(Color.WHITE)
                textSize = 20f
                gravity = Gravity.CENTER
                setPadding(40, 40, 40, 40)
            }

            addView(warningText, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            })
        }

        setContentView(root)

        // Prevent back navigation or interaction
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    override fun onBackPressed() {
        // Disable back press
    }
}