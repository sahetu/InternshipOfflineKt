package internship.offlinekt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var submit : Button
    lateinit var createAccount : TextView
    var context : Context = this

    lateinit var db : SQLiteDatabase
    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

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
                    var selectQuery : String = "SELECT * FROM USERS WHERE (USERNAME='"+email.text.toString()+"' OR CONTACT='"+email.text.toString()+"' OR EMAIL='"+email.text.toString()+"' ) AND password='"+password.text.toString()+"'"
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
}