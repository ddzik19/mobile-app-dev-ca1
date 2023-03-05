package ie.wit.ca1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class CollectionModel(
    var id: Int = 0,
    var title: String = "",
    var genre: String = "",
    var cards: ArrayList<CardModel> = ArrayList()
): Parcelable
