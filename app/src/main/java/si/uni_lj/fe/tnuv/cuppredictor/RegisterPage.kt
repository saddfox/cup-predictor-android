package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService
import si.uni_lj.fe.tnuv.cuppredictor.models.UserInfo

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
    }

    fun registerButton(view: View) {
        val emailField: TextView = findViewById(R.id.RegisterInputEmail)
        val email: String = emailField.text.toString()
        val pwdField: TextView = findViewById(R.id.RegisterInputPassword)
        val password: String = pwdField.text.toString()
        val nameField: TextView = findViewById(R.id.RegisterInputUsername)
        val name: String = nameField.text.toString()

        RestApiService().registerUser(
            UserInfo(
                userEmail = email,
                userPwd = password,
                userName = name
            )
        ) {
            if (it?.userId != null) {
                Log.d("cup", it.userId.toString())

                startActivity(Intent(this, LoginPage::class.java))
                finish()

            } else {
                Log.d("cup", "error creating account")
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun switchToLogin(view: View) {
        startActivity(Intent(this, LoginPage::class.java))
    }

}