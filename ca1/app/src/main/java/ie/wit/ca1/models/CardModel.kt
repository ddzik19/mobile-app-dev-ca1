package ie.wit.ca1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardModel(
    var id: Int = 0,
    var collectionId: Int = 0,
    var cardName: String = "",
    var cardNumber: String = "",
    var cardRarity: String = "",
    var cardType: String = "",
    var isCollected: Boolean = false
): Parcelable
