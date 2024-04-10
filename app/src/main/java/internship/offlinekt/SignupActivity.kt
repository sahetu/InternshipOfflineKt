package internship.offlinekt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
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

    lateinit var city : Spinner
    val cityArray = arrayOf("Select City","Ahmedabad","Vadodara","Surat","Rajkot","Gandhinagar")

    var sCity : String = ""

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

        city = findViewById(R.id.signup_city)
        //ArrayAdapter adapter = new ArrayAdapter(Context,Layout,array);
        var arrayAdapter : ArrayAdapter<*> = ArrayAdapter(this@SignupActivity,android.R.layout.simple_list_item_1,cityArray)
        //city.setAdapter(adapter)
        city.adapter = arrayAdapter

        city.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //TODO("Not yet implemented")
                if (p2==0) {
                    sCity = ""
                    Log.d("RESPONSE_POSITION_IF","$p2 _ Selected City = "+sCity)
                }
                else {
                    sCity = cityArray[p2]
                    Log.d("RESPONSE_POSITION_ELSE","$p2 _ Selected City = "+sCity)
                    CommonMethod().ToasFunction(this@SignupActivity, cityArray[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

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