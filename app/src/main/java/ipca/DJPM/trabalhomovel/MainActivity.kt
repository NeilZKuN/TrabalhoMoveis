package ipca.DJPM.trabalhomovel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.layout_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    data class User(val email: String, val password: String)

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference
/*
        val intent = Intent(this, LoginActivity2::class.java)
        startActivity(intent)
 */

        /*
        val listaReceitas = findViewById<RecyclerView>(R.id.rv_recipes_list)

        listaReceitas.layoutManager = LinearLayoutManager(this)
        listaReceitas.setHasFixedSize(true)
        getRecipeData { receitas : List<RecipeData> ->
            for (receita in receitas){
                Log.d("tag", receita.id.toString())
            }
            var adapter = RecipeAdapter(receitas)
            listaReceitas.adapter = adapter

            adapter.setOnItemClickListener(object : RecipeAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(this@MainActivity, "ola$position", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, RecipeInfo3::class.java)
                    intent.putExtra("title", receitas[position].title)
                    intent. putExtra("servings", receitas[position].servings)
                    intent.putExtra("time", receitas[position].ready_time)
                    intent.putExtra("image", receitas[position].image)
                    intent.putExtra("summary", receitas[position].summary)
                    startActivity(intent)
                }

            })
        }

         */


        //val toolbar = findViewById(R.id.layout_toolbar)

        createNotifChannel()



        var email = findViewById<EditText>(R.id.et_user_name)
        var password = findViewById<EditText>(R.id.et_password)
        var button_register = findViewById<Button>(R.id.button_register)
        var button_submit = findViewById<Button>(R.id.button_submit)

        button_register.setOnClickListener {
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



        button_submit.setOnClickListener {
            val user_name = email.text.toString()
            val password = password.text.toString()

            if (user_name.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(user_name, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            db.child("users").child(user!!.uid).child("email").setValue(user_name)
                        } else {
                            Toast.makeText(this, "Error: No Corresponding user info. Signed in as Guest", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }

            //Codigo para continuar para o outro layout se no caso for verdadeiro

            if (user_name.toString() != "" && password.toString() != "") {
                setContentView(R.layout.layout_menu)

                val notif = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Ola $user_name")
                    .setSmallIcon(R.drawable.pizza)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

                    .build()

                val notifManager = NotificationManagerCompat.from(this)

                notifManager.notify(NOTIF_ID,notif)

                val button_receitas = findViewById(R.id.buttom_receitas) as Button

                button_receitas.setOnClickListener {
                    //codigo do ir para as receitas aqui
                    setContentView(R.layout.layout_receitas)
                    val listaReceitas = findViewById<RecyclerView>(R.id.rv_recipes_list)

                    listaReceitas.layoutManager = LinearLayoutManager(this)
                    listaReceitas.setHasFixedSize(true)
                    getRecipeData { receitas: List<RecipeData> ->
                        for (receita in receitas) {
                            Log.d("tag", receita.id.toString())
                        }
                        var adapter = RecipeAdapter(receitas)
                        listaReceitas.adapter = adapter

                        adapter.setOnItemClickListener(object : RecipeAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {

                                val intent = Intent(this@MainActivity, RecipeInfo3::class.java)
                                intent.putExtra("title", receitas[position].title)
                                intent.putExtra("servings", receitas[position].servings)
                                intent.putExtra("time", receitas[position].ready_time)
                                intent.putExtra("summary", receitas[position].summary)
                                startActivity(intent)
                            }

                        })
                    }
                }
            }

        }
    }
    fun registerUser(txtEmail: String, txtPassword: String) {
        auth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(this){ task->
            if(task.isSuccessful){
                Toast.makeText(this, "Registro feito com sucesso!", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"Falha no registro", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getRecipeData(callback: (List<RecipeData>) -> Unit) {
        val apiService = ApiService.getInstance().create(SearchInterface::class.java)
        apiService.getRandomRecipes().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                return callback(response.body()!!.receitas!!)
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {

            }

        })
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply{
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}