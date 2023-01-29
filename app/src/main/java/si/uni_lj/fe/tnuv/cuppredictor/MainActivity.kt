package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService
import si.uni_lj.fe.tnuv.cuppredictor.models.UserInfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // compare expiry time of auth_token with current time
        // refresh it if its about to expire
        val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("auth_token", null)
        val expiry = sharedPreference.getInt("expiry", 0)
        Log.d("cup", "current auth token is" + token.toString())
        if (token.isNullOrEmpty()) {
            Log.d("cup", "token is null/empty, starting register page")
            startActivity(Intent(this, RegisterPage::class.java))
            finish()
        } else if (expiry < ((System.currentTimeMillis() / 1000) + 1800)) {
            Log.d(
                "cup",
                "current token" + expiry.toString() + "current time" + ((System.currentTimeMillis() / 1000) + 1800).toString()
            )
            Log.d("cup", "Token expired, getting new one")
            val email = sharedPreference.getString("email", "")
            val password = sharedPreference.getString("pwd", "")

            RestApiService().loginUser(
                UserInfo(
                    userEmail = email,
                    userPwd = password
                )
            ) {
                if ((it?.auth_token != null) && (it.auth_token != "")) {
                    Log.d("cup", "get new token: " + it.auth_token.toString())
                    //val sharedPreference =  getSharedPreferences("preferences", Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putString("auth_token", it.auth_token)
                    editor.putInt("expiry", it.expiry!!)
                    editor.apply()
                    Log.d("cup", "renewed token, starting cup list")
                    startActivity(Intent(this, CupList::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.d("cup", "token renew failed, starting login page")
                    startActivity(Intent(this, LoginPage::class.java))
                    finish()
                }
            }
        } else {
            Log.d("cup", "old token is still valid, start cup list")
            Log.d(
                "cup",
                "current token" + expiry.toString() + "current time" + ((System.currentTimeMillis() / 1000) + 1800).toString()
            )

            startActivity(Intent(this, CupList::class.java))
            finish()
        }
    }
}

