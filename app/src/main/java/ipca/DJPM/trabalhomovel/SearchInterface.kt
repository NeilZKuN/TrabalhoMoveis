package ipca.DJPM.trabalhomovel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {
    @GET("/recipes/random?apiKey=f6e3d0003abd4c96aa0733043fc073f3&number=20")
    fun getRandomRecipes(
        //@Query("tags") tags : String
    ) : Call<RecipeResponse>
}