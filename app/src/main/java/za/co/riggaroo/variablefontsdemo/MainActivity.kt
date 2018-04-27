package za.co.riggaroo.variablefontsdemo

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentWeight = 1
    private var currentWidth = 1
    private var currentMotionValue = -1f //Can be a value from -1F to 1.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setFontWidthWeight(currentWidth, currentWeight)

        setZyconFont(currentMotionValue)
        setupZyconFontAnimation()

        seekBarWeight.onProgressChanged { progress ->
            currentWeight = progress * 10
            setFontWidthWeight(currentWidth, currentWeight)
        }

        seekBarWidth.onProgressChanged { progress ->
            currentWidth = progress * 10
            setFontWidthWeight(currentWidth, currentWeight)
        }

        seekBarT1.onProgressChanged { progress ->
            currentMotionValue = progress.toFloat() / 100f
            setZyconFont(currentMotionValue)

        }
    }

    private fun setZyconFont(progress: Float) {
        val builder = Typeface.Builder(assets, "fonts/Zycon.ttf")

        builder.setFontVariationSettings("'M1  ' $progress")

        textViewFontZycon.typeface = builder.build()
    }

    private fun setFontWidthWeight(width: Int, weight: Int) {
        val builder = Typeface.Builder(assets, "fonts/MutatorSans.ttf")

        builder.setFontVariationSettings("'wght' $weight, 'wdth' $width")

        textViewFont.typeface = builder.build()
    }

    private fun setupZyconFontAnimation() {
        val handler = Handler()
        val delay = 100L //milliseconds

        handler.postDelayed(object : Runnable {
            override fun run() {
                setZyconFont(currentMotionValue)

                currentMotionValue = Math.sin(System.currentTimeMillis().toDouble() / 1000).toFloat() //Just some maths to ossilate a value between 1 and -1
                handler.postDelayed(this, delay)
            }
        }, delay)
    }
}

fun SeekBar.onProgressChanged(progressCallback: (Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            progressCallback(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }
    })
}
