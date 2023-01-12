package ipca.DJPM.trabalhomovel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RecipeData(

    val id : Int?,
    val title : String?,
    val summary : String?,
    val image : String?,
    @SerializedName("readyInMinutes")
    val ready_time : Int?,
    val servings : Int?,
)
