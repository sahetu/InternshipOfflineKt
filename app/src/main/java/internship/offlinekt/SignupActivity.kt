package internship.offlinekt

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    lateinit var userName : EditText
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var contact : EditText
    lateinit var password : EditText
    lateinit var confirmPassword : EditText

    lateinit var terms : CheckBox

    lateinit var gender : RadioGroup

    lateinit var submit : Button

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    lateinit var sGender : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userName = findViewById(R.id.signup_username)
        name = findViewById(R.id.signup_name)
        email = findViewById(R.id.signup_email)
        contact = findViewById(R.id.signup_contact)
        password = findViewById(R.id.signup_password)
        confirmPassword = findViewById(R.id.signup_confirm_password)

        gender = findViewById(R.id.signup_gender)

        gender.setOnCheckedChangeListener { radioGroup, i ->
            val rb = findViewById<RadioButton>(i)
            sGender = rb.text.toString()
            CommonMethod().ToasFunction(this@SignupActivity, sGender)
        }

        terms = findViewById(R.id.signup_check)

        submit = findViewById(R.id.signup_button)

        submit.setOnClickListener{
            if(userName.text.toString().trim().equals("")){
                userName.error = "Username Required"
            }
            else if(name.text.toString().trim().equals("")){
                name.error = "Name Required"
            }
            else if(email.text.toString().trim().equals("")){
                email.error = "Email Required"
            }
            else if(!email.text.toString().trim().matches(Regex(emailPattern))){
                email.error = "Valid Email Id Required"
            }
            else if(contact.text.toString().trim().equals("")){
                contact.error = "Contact No. Required"
            }
            else if(contact.text.toString().trim().length<10 || contact.text.toString().trim().length>10){
                contact.error = "Valid Contact No. Required"
            }
            else if(password.text.toString().trim().equals("")){
                password.error = "Password Required"
            }
            else if(confirmPassword.text.toString().trim().equals("")){
                confirmPassword.error = "Confirm Password Required"
            }
            else if(!password.text.toString().trim().matches(Regex(confirmPassword.text.toString()))){
                confirmPassword.error = "Confirm Password Does Not Match"
            }
            else if(gender.checkedRadioButtonId == -1){
                CommonMethod().ToasFunction(this@SignupActivity,"Please Select Gender")
            }
            else if(!terms.isChecked){
                CommonMethod().ToasFunction(this@SignupActivity,"Please Accept Terms & Conditions")
            }
            else{
                CommonMethod().ToasFunction(this@SignupActivity,"Signup Successfully")
            }
        }


    }
}