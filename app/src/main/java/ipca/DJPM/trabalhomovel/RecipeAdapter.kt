package ipca.DJPM.trabalhomovel

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_listview_receitas.view.*

class RecipeAdapter(
    private val recipes : List<RecipeData>
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }

    class RecipeViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){

        fun bindRecipe(recipe : RecipeData){
            itemView.textViewReceitaTitle.text = recipe.title
            itemView.textViewReceitaBody.text = recipe.summary
            Glide.with(itemView).load(recipe.image).into(itemView.imageViewReceitaImagem)
        }

        init {
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_listview_receitas, parent, false),
            mListener
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindRecipe(recipes.get(position))

    }

    override fun getItemCount(): Int = recipes.size


}
