package internship.offlinekt

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class RazorpayDemoActivity : AppCompatActivity(),PaymentResultWithDataListener {

    lateinit var payNow : Button
    lateinit var amount : EditText
    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razorpay_demo)

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE)

        payNow = findViewById(R.id.razorpay_pay)
        amount = findViewById(R.id.razorpay_amount)

        payNow.setOnClickListener {
            if(amount.text.toString().trim().equals("")){
                amount.error = "Amount Required"
            }
            else if(amount.text.toString().trim().equals("0")){
                amount.error = "Valid Amount Required"
            }
            else{
                startPayment()
            }
        }

    }

    private fun startPayment() {
        //TODO("Not yet implemented")
        val co = Checkout()

        co.setKeyID("rzp_test_xsiOz9lYtWKHgF")
        try {
            val options = JSONObject()
            options.put("name", getResources().getString(R.string.app_name))
            options.put(
                "description",
                "Purchase Deal From " + getResources().getString(R.string.app_name)
            )
            options.put("send_sms_hash", true)
            options.put("allow_rotation", true)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher)
            options.put("currency", "INR")
            options.put("amount", (amount.text.toString().toInt() * 100).toString())
            val preFill = JSONObject()
            preFill.put("email", sp.getString(ConstantSp.EMAIL, ""))
            preFill.put("contact", sp.getString(ConstantSp.CONTACT, ""))
            options.put("prefill", preFill)
            co.open(this@RazorpayDemoActivity, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        //TODO("Not yet implemented")
        try {
            val builder = AlertDialog.Builder(this@RazorpayDemoActivity)
            builder.setTitle("Payment Successfully")
            builder.setMessage("Transaction Id : $p0")
            builder.setPositiveButton(
                "Dismiss"
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
                onBackPressed()
            }
            builder.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            onBackPressed()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        //TODO("Not yet implemented")
        try {
            val builder = AlertDialog.Builder(this@RazorpayDemoActivity)
            builder.setTitle("Payment Failed")
            builder.setMessage(p2?.getData().toString())
            builder.setPositiveButton(
                "Dismiss"
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
                onBackPressed()
            }
            builder.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            onBackPressed()
        }
    }
}