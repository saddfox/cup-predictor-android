package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService
import si.uni_lj.fe.tnuv.cuppredictor.models.UserInfo

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

    }

    fun loginButton(view: View) {
        val emailField: TextView = findViewById(R.id.loginInputEmail)
        val email: String = emailField.text.toString()
        val pwdField: TextView = findViewById(R.id.loginInputPassword)
        val password: String = pwdField.text.toString()

        RestApiService().loginUser(
            UserInfo(
                userEmail = email,
                userPwd = password
            )
        ) {
            if ((it?.auth_token != null) && (it.auth_token != "")) {
                Log.d("cup", it.auth_token.toString())
                val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("auth_token", it.auth_token)
                editor.putInt("expiry", it.expiry!!)
                editor.putString("email", email)
                editor.putString("pwd", password)
                editor.apply()

                startActivity(Intent(this, CupList::class.java))
                finish()

            } else {
                Log.d("cup", "error logging in")
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun switchToRegister(view: View) {
        startActivity(Intent(this, RegisterPage::class.java))
    }
}