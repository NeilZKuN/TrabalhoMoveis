package ipca.DJPM.trabalhomovel

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth



class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerb: Button
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        email = findViewById(R.id.et_user_name)
        password = findViewById(R.id.et_password)
        registerb = findViewById(R.id.button_register)


        firebaseAuth = FirebaseAuth.getInstance()

        registerb.setOnClickListener{
            var txt_email : String = email.text.toString()
            var txt_password : String = password.text.toString()

            if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                Toast.makeText(this, "Credenciais vazias", Toast.LENGTH_SHORT).show()
            } else if(txt_password.length < 5){
                Toast.makeText(this,"Tamanho de palavra-passe insuficiente",Toast.LENGTH_SHORT).show()
            }
            else{
                registerUser(txt_email,txt_password)
            }
        }
    }

    public fun registerUser(txtEmail: String, txtPassword: String) {
        firebaseAuth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(this){ task->
            if(task.isSuccessful){
                Toast.makeText(this, "Registro feito com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
                finish()
            } else{
                Toast.makeText(this,"Falha no registro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}