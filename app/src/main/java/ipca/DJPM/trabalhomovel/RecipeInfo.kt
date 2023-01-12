package ipca.DJPM.trabalhomovel

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_receita_dados.*

class RecipeInfo : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_receita_dados)

        val rTitle = findViewById<TextView>(R.id.RecipeTitle)
        val rServings = findViewById<TextView>(R.id.RecipeServings)
        val rTime = findViewById<TextView>(R.id.RecipeMinutes)
        val rSummary = findViewById<TextView>(R.id.RecipeSummary)

        val bundle : Bundle?= intent.extras
        val title = bundle!!.getString("title")
        val servings = bundle!!.getString("servings")
        val time = bundle!!.getString("time")
        val summary = bundle!!.getString("summary")

        rTitle.text = title
        RecipeServings.text = servings
        rTime.text = time
        rSummary.text = summary
    }
}