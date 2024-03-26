package internship.offlinekt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var submit : Button
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.main_email)
        password = findViewById(R.id.main_password)

        submit = findViewById(R.id.main_login)

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
                    CommonMethod().ToasFunction(this@MainActivity,"Login Successfully")
                    CommonMethod().SnackBarFun(p0!!,"Login Successfully")

                    /*var intent : Intent = Intent(this@MainActivity,DashboardActivity::class.java)
                    startActivity(intent)*/
                    CommonMethod().IntentFun(this@MainActivity,DashboardActivity::class.java)

                }
            }
        })

    }
}