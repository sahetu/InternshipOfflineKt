package internship.offlinekt

import android.app.ProgressDialog
import android.batch1.MakeServiceCall
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var submit : Button
    lateinit var createAccount : TextView
    var context : Context = this

    lateinit var db : SQLiteDatabase
    lateinit var sp : SharedPreferences

    lateinit var apiInterface : ApiInterface
    lateinit var pd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

        apiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        db = openOrCreateDatabase("InternshipKt", MODE_PRIVATE,null)
        var tableQuery : String = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(50))"
        db.execSQL(tableQuery)

        email = findViewById(R.id.main_email)
        password = findViewById(R.id.main_password)

        submit = findViewById(R.id.main_login)

        createAccount = findViewById(R.id.main_create_account)
        createAccount.setOnClickListener {
            CommonMethod().IntentFun(this@MainActivity,SignupActivity::class.java)
        }

        /*submit.setOnClickListener {
            //Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()
            if(email.text.toString().trim().equals("")){
                email.error = "Email Id Requiired"
            }
            else if (password.text.toString().trim().equals("")){
                password.error = "Password Required"
            }
            else if (password.text.toString().trim().length < 6){
                password.error = "Min. 6 Char Password Required"
            }
            else {
                //Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_SHORT).show()
                CommonMethod().ToasFunction(this@MainActivity,"Login Successfully")
                CommonMethod().SnackBarFun(it,"Login Successfully")
            }
        }*/

        submit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                //TODO("Not yet implemented")
                if(email.text.toString().trim().equals("")){
                    email.error = "Email Id Requiired"
                }
                else if (password.text.toString().trim().equals("")){
                    password.error = "Password Required"
                }
                else if (password.text.toString().trim().length < 6){
                    password.error = "Min. 6 Char Password Required"
                }
                else {
                    //Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_SHORT).show()
                    /*var selectQuery : String = "SELECT * FROM USERS WHERE (USERNAME='"+email.text.toString()+"' OR CONTACT='"+email.text.toString()+"' OR EMAIL='"+email.text.toString()+"' ) AND password='"+password.text.toString()+"'"
                    var cursor : Cursor = db.rawQuery(selectQuery,null)
                    if(cursor.count>0){
                        while (cursor.moveToNext()){
                            sp.edit().putString(ConstantSp.USERID,cursor.getString(0)).commit()
                            sp.edit().putString(ConstantSp.USERNAME,cursor.getString(1)).commit()
                            sp.edit().putString(ConstantSp.NAME,cursor.getString(2)).commit()
                            sp.edit().putString(ConstantSp.EMAIL,cursor.getString(3)).commit()
                            sp.edit().putString(ConstantSp.CONTACT,cursor.getString(4)).commit()
                            sp.edit().putString(ConstantSp.PASSWORD,cursor.getString(5)).commit()
                            sp.edit().putString(ConstantSp.GENDER,cursor.getString(6)).commit()
                            sp.edit().putString(ConstantSp.CITY,cursor.getString(7)).commit()
                        }

                        CommonMethod().ToasFunction(this@MainActivity,"Login Successfully")
                        CommonMethod().SnackBarFun(p0!!,"Login Successfully")
                        CommonMethod().IntentFun(this@MainActivity,DashboardActivity::class.java)
                    }
                    else{
                        CommonMethod().ToasFunction(this@MainActivity,"Login Unsuccessfully")
                        CommonMethod().SnackBarFun(p0!!,"Login Unsuccessfully")
                    }*/

                    if(ConnectionDetector(this@MainActivity).networkConnected()){
                        //doLogin(this@MainActivity,email.text.toString(),password.text.toString(),sp).execute()
                        pd = ProgressDialog(this@MainActivity)
                        pd.setMessage("Please Wait...")
                        pd.setCancelable(false)
                        pd.show()
                        doLoginRetrofit()
                    }
                    else{
                        ConnectionDetector(this@MainActivity).networkDisconnected()
                    }

                    /*var intent : Intent = Intent(this@MainActivity,DashboardActivity::class.java)
                    var bundle : Bundle = Bundle()
                    bundle.putString("EMAIL",email.text.toString().trim())
                    intent.putExtras(bundle)
                    startActivity(intent)*/
                    //CommonMethod().IntentFun(this@MainActivity,DashboardActivity::class.java)

                }
            }
        })

    }

    private fun doLoginRetrofit() {
        //TODO("Not yet implemented")
        var call : Call<GetLoginData> = apiInterface.getLoginData(
            email.text.toString(),
            password.text.toString()
        )

        call.enqueue(object : Callback<GetLoginData> {
            override fun onResponse(call: Call<GetLoginData>, response: Response<GetLoginData>) {
                //TODO("Not yet implemented")
                pd.dismiss()
                if(response.code() == 200){
                    if(response.body()?.status!!){
                        CommonMethod().ToasFunction(this@MainActivity, response.body()!!.message!!)
                        for (i in 0..response.body()!!.userDetails!!.size-1){
                            sp.edit().putString(ConstantSp.USERID,response.body()!!.userDetails!!.get(i).userId).commit()
                            sp.edit().putString(ConstantSp.USERNAME,response.body()!!.userDetails!!.get(i).userName).commit()
                            sp.edit().putString(ConstantSp.NAME,response.body()!!.userDetails!!.get(i).name).commit()
                            sp.edit().putString(ConstantSp.EMAIL,response.body()!!.userDetails!!.get(i).email).commit()
                            sp.edit().putString(ConstantSp.CONTACT,response.body()!!.userDetails!!.get(i).contact).commit()
                            sp.edit().putString(ConstantSp.PASSWORD,"").commit()
                            sp.edit().putString(ConstantSp.GENDER,response.body()!!.userDetails!!.get(i).gender).commit()
                            sp.edit().putString(ConstantSp.CITY,response.body()!!.userDetails!!.get(i).city).commit()
                        }
                        CommonMethod().IntentFun(context,DashboardActivity::class.java)
                    }
                    else{
                        CommonMethod().ToasFunction(this@MainActivity, response.body()!!.message!!)
                    }
                }
                else{
                    CommonMethod().ToasFunction(this@MainActivity,"Server Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GetLoginData>, t: Throwable) {
                //TODO("Not yet implemented")
                pd.dismiss()
                CommonMethod().ToasFunction(this@MainActivity,t.message.toString())
            }

        })
    }

    class doLogin(
        var context: Context,
        var email: String,
        var password: String,
        var sp: SharedPreferences
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
            hashMap.put("email",email)
            hashMap.put("password",password)
            return MakeServiceCall().makeServiceCall(ConstantSp.BASE_URL+"login.php",
                MakeServiceCall.POST,hashMap)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd.dismiss()
            var jsonObject : JSONObject = JSONObject(result)
            if(jsonObject.getBoolean("Status")){
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))

                var jsonArray : JSONArray = jsonObject.getJSONArray("UserDetails")
                for (i in 0..jsonArray.length()-1){
                    var jsonOb : JSONObject = jsonArray.getJSONObject(i)

                    sp.edit().putString(ConstantSp.USERID,jsonOb.getString("userId")).commit()
                    sp.edit().putString(ConstantSp.USERNAME,jsonOb.getString("userName")).commit()
                    sp.edit().putString(ConstantSp.NAME,jsonOb.getString("name")).commit()
                    sp.edit().putString(ConstantSp.EMAIL,jsonOb.getString("email")).commit()
                    sp.edit().putString(ConstantSp.CONTACT,jsonOb.getString("contact")).commit()
                    sp.edit().putString(ConstantSp.PASSWORD,"").commit()
                    sp.edit().putString(ConstantSp.GENDER,jsonOb.getString("gender")).commit()
                    sp.edit().putString(ConstantSp.CITY,jsonOb.getString("city")).commit()
                }
                CommonMethod().IntentFun(context,DashboardActivity::class.java)
            }
            else{
                CommonMethod().ToasFunction(context,jsonObject.getString("Message"))
            }
        }
    }
}