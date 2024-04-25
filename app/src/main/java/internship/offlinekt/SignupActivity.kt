package internship.offlinekt

import android.app.ProgressDialog
import android.batch1.MakeServiceCall
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    lateinit var db : SQLiteDatabase

    lateinit var apiInterface : ApiInterface
    lateinit var pd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = openOrCreateDatabase("InternshipKt", MODE_PRIVATE,null)
        var tableQuery : String = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(50))"
        db.execSQL(tableQuery)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

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
                var selectQuery : String = "SELECT * FROM USERS WHERE email='"+email.text.toString()+"' OR contact='"+contact.text.toString()+"'"
                var cursor : Cursor = db.rawQuery(selectQuery,null)
                if(cursor.count>0){
                    CommonMethod().ToasFunction(this@SignupActivity, "Email/Contact No. Already Registered")
                }
                else {
                    /*var insertQuery: String =
                        "INSERT INTO USERS VALUES (NULL,'" + userName.text.toString() + "','" + name.text.toString() + "','" + email.text.toString() + "','" + contact.text.toString() + "','" + password.text.toString() + "','" + sGender + "','" + sCity + "')";
                    db.execSQL(insertQuery)
                    CommonMethod().ToasFunction(this@SignupActivity, "Signup Successfully")
                    onBackPressed()*/
                    if(ConnectionDetector(this@SignupActivity).networkConnected()){
                        /*doSignup(
                            this@SignupActivity,
                            userName.text.toString(),
                            name.text.toString(),
                            email.text.toString(),
                            contact.text.toString(),
                            password.text.toString(),
                            sGender,
                            sCity
                        ).execute()*/
                        pd = ProgressDialog(this@SignupActivity)
                        pd.setMessage("Please Wait...")
                        pd.setCancelable(false)
                        pd.show()
                        doSignupRetrofit()
                    }
                    else{
                        ConnectionDetector(this@SignupActivity).networkDisconnected()
                    }
                }
            }
        }


    }

    private fun doSignupRetrofit() {
        //TODO("Not yet implemented")
        var call : Call<GetSignupData> = apiInterface.getSignupData(
            userName.text.toString(),
            name.text.toString(),
            email.text.toString(),
            contact.text.toString(),
            password.text.toString(),
            sGender,
            sCity
        )

        call.enqueue(object : Callback<GetSignupData>{
            override fun onResponse(call: Call<GetSignupData>, response: Response<GetSignupData>) {
                //TODO("Not yet implemented")
                pd.dismiss()
                if(response.code() == 200){
                    if(response.body()?.status!!){
                        CommonMethod().ToasFunction(this@SignupActivity, response.body()!!.message!!)
                        onBackPressed()
                    }
                    else{
                        CommonMethod().ToasFunction(this@SignupActivity, response.body()!!.message!!)
                    }
                }
                else{
                    CommonMethod().ToasFunction(this@SignupActivity,"Server Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GetSignupData>, t: Throwable) {
                //TODO("Not yet implemented")
                pd.dismiss()
                CommonMethod().ToasFunction(this@SignupActivity,t.message.toString())
            }

        })

    }

    class doSignup(
        var context: Context,
        var sUsername: String,
        var sName: String,
        var sEmail: String,
        var sContact: String,
        var sPassword: String,
        var sGender: String,
        var sCity: String
    ): AsyncTask<String,String,String>() {

        lateinit var pd : ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(context)
            pd.setTitle("Please Wait...")
            pd.setCancelable(false)
            pd.show()
        }

        override fun doInBackground(vararg p0: String?): String {
            //TODO("Not yet implemented")
            var hashMap : HashMap<String,String> = HashMap()
            hashMap.put("username",sUsername)
            hashMap.put("name",sName)
            hashMap.put("email",sEmail)
            hashMap.put("contact",sContact)
            hashMap.put("password",sPassword)
            hashMap.put("gender",sGender)
            hashMap.put("city",sCity)
            return MakeServiceCall().makeServiceCall(ConstantSp.BASE_URL+"signup.php",MakeServiceCall.POST,hashMap)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd.dismiss()
            var jsonObject : JSONObject = JSONObject(result)
            if(jsonObject.getBoolean("Status")){
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))
                CommonMethod().IntentFun(context,MainActivity::class.java)
            }
            else{
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))
            }
        }

    }
}