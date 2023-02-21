package ie.wit.ca1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionModel(
    var id: Long = 0,
    var title: String = "",
    var genre: String = "",
    var cards: ArrayList<String> = ArrayList()
): Parcelable
