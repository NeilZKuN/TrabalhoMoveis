package ipca.DJPM.trabalhomovel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_listview_receitas.view.*

class RecipeInfo3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_receita_dados)

        val rTitle = findViewById<TextView>(R.id.RecipeTitle)
        val rServings = findViewById<TextView>(R.id.RecipeServings)
        val rTime = findViewById<TextView>(R.id.RecipeMinutes)
        val rSummary = findViewById<TextView>(R.id.RecipeSummary)

        val bundle : Bundle?= intent.extras
        val title = bundle!!.getString("title")
        val servings = bundle!!.getInt("servings")
        val time = bundle!!.getInt("time")
        val summary = bundle!!.getString("summary")

        rTitle.text = title
        rServings.text = servings.toString()
        rTime.text = time.toString()
        rSummary.text = summary
    }
}