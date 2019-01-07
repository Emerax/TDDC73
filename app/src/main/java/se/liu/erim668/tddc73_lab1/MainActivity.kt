package se.liu.erim668.tddc73_lab1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity1_xml)
        generateActivityOneKotlin()
    }

    fun generateActivityOneKotlin() {
        val layout: ConstraintLayout = ConstraintLayout(this)
        layout.id = R.id.layout

        val button: Button = Button(this)
        button.id = R.id.activity_one_button
        button.setText(R.string.activity1_buttonText)
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val textField: EditText = EditText(this)
        textField.id = R.id.activity_one_textField
        textField.setText(R.string.activity1_plainText)
        textField.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val ratingBar: RatingBar = RatingBar(this)
        ratingBar.id = R.id.activity_one_ratingBar
        ratingBar.numStars = 5
        ratingBar.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val multiLineText: EditText = EditText(this)
        multiLineText.id = R.id.activity_one_multiText
        multiLineText.setText(R.string.activity1_multiLineText)
        multiLineText.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)

        layout.addView(button)
        layout.addView(textField)
        layout.addView(ratingBar)
        layout.addView(multiLineText)

        val set: ConstraintSet = ConstraintSet()
        set.clone(layout)

        //Connect button to top and sides of layout
        set.connect(button.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 0)
        set.connect(button.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(button.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 0)
        //Connect textField to sides of layout, bottom of button.
        set.connect(textField.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(textField.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 0)
        set.connect(textField.id, ConstraintSet.TOP, button.id, ConstraintSet.BOTTOM, 0)
        //Connect ratingBar to sides of layout, bottom of textField
        set.connect(ratingBar.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 16)
        set.connect(ratingBar.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 16)
        set.connect(ratingBar.id, ConstraintSet.TOP, textField.id, ConstraintSet.BOTTOM, 0)
        //Connect multiLineText to sides and bottom of layout, bottom of ratingBar
        set.connect(multiLineText.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(multiLineText.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 0)
        set.connect(multiLineText.id, ConstraintSet.BOTTOM, layout.id, ConstraintSet.BOTTOM, 0)
        set.connect(multiLineText.id, ConstraintSet.TOP, ratingBar.id, ConstraintSet.BOTTOM, 0)

        set.applyTo(layout)

        setContentView(layout)
    }
}
