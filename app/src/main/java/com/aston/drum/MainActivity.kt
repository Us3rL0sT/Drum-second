package com.aston.drum

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var drumView: DrumView
    private lateinit var resetButton: Button
    private lateinit var imageView: ImageView
    private lateinit var colorText: ColorTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drumView = findViewById(R.id.drumView)
        resetButton = findViewById(R.id.resetButton)
        imageView = findViewById(R.id.imageView)
        colorText = findViewById(R.id.colorText)
        drumView.setColorTextView(colorText)


        drumView.setOnClickListener {
            drumView.rotateDrum { currentColor ->
                if (isColorInList(currentColor, listOf(
                        Color.rgb(255,127,0),
                        Color.rgb(0,255,0),
                        Color.rgb(0,0,255)
                    ))
                ) {
                    loadImageFromUrl("https://loremflickr.com/640/360")
                    colorText.post {
                        colorText.text = ""
                    }

                } else {
                    clearImage()
                    val colorName = drumView.getColorName(currentColor)
                    colorText.post {
                        colorText.text = colorName
                    }

                }
            }
        }

        resetButton.setOnClickListener {
            colorText.post {
                colorText.text = ""
            }
            clearImage()
        }
    }


    private fun isColorInList(color: Int, colorList: List<Int>): Boolean {
        return colorList.contains(color)
    }

    private fun loadImageFromUrl(url: String) {
        Picasso.get().load(url).into(imageView)
    }

    private fun clearImage() {
        imageView.setImageDrawable(null)
    }



}