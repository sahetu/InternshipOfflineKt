package internship.offlinekt

import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.DrmInitData.SchemeInitData
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {

    lateinit var userName : EditText
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var contact : EditText
    lateinit var password : EditText
    lateinit var confirmPassword : EditText

    lateinit var gender : RadioGroup
    lateinit var male : RadioButton
    lateinit var female : RadioButton

    lateinit var submit : Button
    lateinit var editProfile : Button

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    lateinit var sGender : String

    lateinit var city : Spinner
    val cityArray = arrayOf("Select City","Ahmedabad","Vadodara","Surat","Rajkot","Gandhinagar")

    var sCity : String = ""

    lateinit var db : SQLiteDatabase
    lateinit var sp : SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        db = openOrCreateDatabase("InternshipKt", MODE_PRIVATE,null)
        var tableQuery : String = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(50))"
        db.execSQL(tableQuery)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

        userName = findViewById(R.id.profile_username)
        name = findViewById(R.id.profile_name)
        email = findViewById(R.id.profile_email)
        contact = findViewById(R.id.profile_contact)
        password = findViewById(R.id.profile_password)
        confirmPassword = findViewById(R.id.profile_confirm_password)

        gender = findViewById(R.id.profile_gender)
        male = findViewById(R.id.profile_male)
        female = findViewById(R.id.profile_female)

        gender.setOnCheckedChangeListener { radioGroup, i ->
            val rb = findViewById<RadioButton>(i)
            sGender = rb.text.toString()
            CommonMethod().ToasFunction(this@ProfileActivity, sGender)
        }

        city = findViewById(R.id.profile_city)
        //ArrayAdapter adapter = new ArrayAdapter(Context,Layout,array);
        var arrayAdapter : ArrayAdapter<*> = ArrayAdapter(this@ProfileActivity,android.R.layout.simple_list_item_1,cityArray)
        //city.setAdapter(adapter)
        city.adapter = arrayAdapter

        city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //TODO("Not yet implemented")
                if (p2==0) {
                    sCity = ""
                    Log.d("RESPONSE_POSITION_IF","$p2 _ Selected City = "+sCity)
                }
                else {
                    sCity = cityArray[p2]
                    Log.d("RESPONSE_POSITION_ELSE","$p2 _ Selected City = "+sCity)
                    CommonMethod().ToasFunction(this@ProfileActivity, cityArray[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        editProfile = findViewById(R.id.profile_edit_button)

        editProfile.setOnClickListener {
            setData(true)
        }

        submit = findViewById(R.id.profile_button)

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
                CommonMethod().ToasFunction(this@ProfileActivity,"Please Select Gender")
            }
            else{
                var selectQuery : String = "SELECT * FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'"
                var cursor : Cursor = db.rawQuery(selectQuery,null)
                if(cursor.count>0){
                    var updateQuery: String =
                        "UPDATE USERS SET USERNAME='"+userName.text.toString()+"',NAME='"+name.text.toString()+"',EMAIL='"+email.text.toString()+"',CONTACT='"+contact.text.toString()+"',PASSWORD='"+password.text.toString()+"',CITY='"+sCity+"',GENDER='"+sGender+"' WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                    db.execSQL(updateQuery)

                    sp.edit().putString(ConstantSp.USERNAME,userName.text.toString()).commit()
                    sp.edit().putString(ConstantSp.NAME,name.text.toString()).commit()
                    sp.edit().putString(ConstantSp.EMAIL,email.text.toString()).commit()
                    sp.edit().putString(ConstantSp.CONTACT,contact.text.toString()).commit()
                    sp.edit().putString(ConstantSp.PASSWORD,password.text.toString()).commit()
                    sp.edit().putString(ConstantSp.GENDER,sGender).commit()
                    sp.edit().putString(ConstantSp.CITY,sCity).commit()

                    CommonMethod().ToasFunction(this@ProfileActivity, "Profile Update Successfully")
                    setData(false)
                }
                else {
                    CommonMethod().ToasFunction(this@ProfileActivity, "Invalid User")
                }
            }
        }

        setData(false)

    }

    private fun setData(b: Boolean) {
        userName.isEnabled = b
        name.isEnabled = b
        email.isEnabled = b
        contact.isEnabled = b
        password.isEnabled = b
        confirmPassword.isEnabled = b

        male.isEnabled = b
        female.isEnabled = b

        if(b){
            confirmPassword.visibility = View.VISIBLE
            submit.visibility = View.VISIBLE
            editProfile.visibility = View.GONE
        }
        else{
            confirmPassword.visibility = View.GONE
            submit.visibility = View.GONE
            editProfile.visibility = View.VISIBLE
        }

        userName.setText(sp.getString(ConstantSp.USERNAME,""))
        name.setText(sp.getString(ConstantSp.NAME,""))
        email.setText(sp.getString(ConstantSp.EMAIL,""))
        contact.setText(sp.getString(ConstantSp.CONTACT,""))
        password.setText(sp.getString(ConstantSp.PASSWORD,""))
        confirmPassword.setText(sp.getString(ConstantSp.PASSWORD,""))

        sGender = sp.getString(ConstantSp.GENDER,"")!!
        if(sGender.equals("Male",true)){
            male.isChecked = true
            female.isChecked = false
        }
        else{
            male.isChecked = false
            female.isChecked = true
        }

        city.isEnabled = b
        sCity = sp.getString(ConstantSp.CITY,"")!!
        var iPosition = 0
        for (i in 0..cityArray.size-1){
            if(sCity.equals(cityArray[i])){
                iPosition = i
                break
            }
        }
        city.setSelection(iPosition)

    }

}