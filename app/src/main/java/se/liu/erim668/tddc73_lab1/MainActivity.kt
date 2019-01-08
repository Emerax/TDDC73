package se.liu.erim668.tddc73_lab1

import android.content.Context
import android.os.Bundle
import android.support.constraint.Barrier
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import se.liu.erim668.tddc73_lab1.R.id.button
import se.liu.erim668.tddc73_lab1.R.id.text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityOne = R.layout.activity1_xml
        val activityTwo = R.layout.activity2_xml
        val activityThree = R.layout.activity3_xml
        setContentView(activityThree)
        //generateActivityOneKotlin()
        //generateActivityTwoKotlin()
    }

    fun generateActivityOneKotlin() {
        val layout = ConstraintLayout(this)
        layout.id = R.id.layout

        val button = Button(this)
        button.id = R.id.activity_one_button
        button.setText(R.string.activity1_buttonText)
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val textField = EditText(this)
        textField.id = R.id.activity_one_textField
        textField.setText(R.string.activity1_plainText)
        textField.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val ratingBar = RatingBar(this)
        ratingBar.id = R.id.activity_one_ratingBar
        ratingBar.numStars = 5
        ratingBar.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val multiLineText = EditText(this)
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

    fun generateActivityTwoKotlin() {
        val layout = ConstraintLayout(this)
        layout.id = R.id.layout

        val nameText = TextView(this)
        nameText.id = R.id.activity_two_nameText
        nameText.setText(R.string.activity_two_nameLabel)
        nameText.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val passwordText = TextView(this)
        passwordText.id = R.id.activity_two_passwordText
        passwordText.setText(R.string.activity_two_passwordText)
        passwordText.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val emailText = TextView(this)
        emailText.id = R.id.activity_two_emailText
        emailText.setText(R.string.activity_two_passwordText)
        emailText.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val ageText = TextView(this)
        ageText.id = R.id.activity_two_ageText
        ageText.setText(R.string.activity_two_ageText)
        ageText.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val textBarrier = Barrier(this)
        textBarrier.id = R.id.activity_two_barrier
        textBarrier.referencedIds = mutableListOf(nameText.id, passwordText.id, emailText.id, ageText.id).toIntArray()
        textBarrier.type = Barrier.RIGHT

        val nameEdit = EditText(this)
        nameEdit.id = R.id.activity_two_nameEdit
        nameEdit.setText(R.string.activity_two_nameInput)
        nameEdit.inputType = EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME
        nameEdit.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val passwordEdit = EditText(this)
        passwordEdit.id = R.id.activity_two_passwordEdit
        passwordEdit.setText(R.string.activity_two_passwordInput)
        passwordEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordEdit.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val emailEdit = EditText(this)
        emailEdit.id = R.id.activity_two_emailEdit
        emailEdit.setText(R.string.activity_two_emailInput)
        emailEdit.inputType = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailEdit.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val seekBar = SeekBar(this)
        seekBar.id = R.id.activity_two_seekBar
        seekBar.progress = 25
        seekBar.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        layout.addView(nameText)
        layout.addView(passwordText)
        layout.addView(emailText)
        layout.addView(ageText)
        layout.addView(textBarrier)
        layout.addView(nameEdit)
        layout.addView(passwordEdit)
        layout.addView(emailEdit)
        layout.addView(seekBar)

        val set = ConstraintSet()
        set.clone(layout)

        set.connect(nameText.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(nameText.id, ConstraintSet.BASELINE, nameEdit.id, ConstraintSet.BASELINE)

        set.connect(passwordText.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(passwordText.id, ConstraintSet.BASELINE, passwordEdit.id, ConstraintSet.BASELINE)

        set.connect(emailText.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(emailText.id, ConstraintSet.BASELINE, emailEdit.id, ConstraintSet.BASELINE)

        set.connect(ageText.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(ageText.id, ConstraintSet.TOP, emailEdit.id, ConstraintSet.BOTTOM, 16)

        set.connect(nameEdit.id, ConstraintSet.START, textBarrier.id, ConstraintSet.END, 0)
        set.connect(nameEdit.id, ConstraintSet.END, layout.id, ConstraintSet.END, 0)
        set.connect(nameEdit.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 16)

        set.connect(passwordEdit.id, ConstraintSet.START, textBarrier.id, ConstraintSet.END, 0)
        set.connect(passwordEdit.id, ConstraintSet.END, layout.id, ConstraintSet.END, 0)
        set.connect(passwordEdit.id, ConstraintSet.TOP, nameEdit.id, ConstraintSet.BOTTOM, 16)

        set.connect(emailEdit.id, ConstraintSet.START, textBarrier.id, ConstraintSet.END, 0)
        set.connect(emailEdit.id, ConstraintSet.END, layout.id, ConstraintSet.END, 0)
        set.connect(emailEdit.id, ConstraintSet.TOP, passwordEdit.id, ConstraintSet.BOTTOM, 16)

        set.connect(seekBar.id, ConstraintSet.START, textBarrier.id, ConstraintSet.END, 0)
        set.connect(seekBar.id, ConstraintSet.END, layout.id, ConstraintSet.END, 0)
        set.connect(seekBar.id, ConstraintSet.TOP, emailEdit.id, ConstraintSet.BOTTOM, 16)
        set.connect(seekBar.id, ConstraintSet.BOTTOM, ageText.id, ConstraintSet.BOTTOM, 0)

        set.applyTo(layout)

        setContentView(layout)
    }
}
