package ipca.DJPM.trabalhomovel

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RecipeResponse(
    @SerializedName("recipes")
    val receitas : List<RecipeData>?
)