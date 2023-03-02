package ie.wit.ca1.models

import android.util.Log.i
import timber.log.Timber.i
import kotlin.random.Random

var id = 0
var cardId = 0
internal fun getNewId(): Int {
    return id ++
}

internal fun getNewCardId(): Int {
    return cardId ++
}

class CollectionMemStore: CollectionStore {
    val collections = ArrayList<CollectionModel>()
    var cards = ArrayList<CardModel>()
    // returning all collections
    override fun findAll(): List<CollectionModel> {
        return collections
    }

    // get all card from a specific collection
    override fun findAllCards(collection: CollectionModel): List<CardModel> {
        var foundCollection: CollectionModel? = collections.find { c -> c.id == collection.id }
        if(foundCollection != null){
            cards = foundCollection.cards
        }
        return cards
    }

    // creating/adding new collections
    override fun create(collection: CollectionModel) {
        collection.id = getNewId()
        collections.add(collection)
        logCollections()
    }

    override fun update(collection: CollectionModel) {
        var foundCollection: CollectionModel? = collections.find { c -> c.id == collection.id }
        if (foundCollection != null) {
            foundCollection.title = collection.title
            foundCollection.genre = collection.genre
            logCollections()
        }
    }

    fun delete(collection: CollectionModel) {
        var foundCollection =  collections.indexOf(collection)
        if (foundCollection != null) {
            collections.removeAt(foundCollection)
            logCollections()
        }
    }

//    adding new card to the collection
//    we find the collection using the id and then we add the card to the cards array
    override fun addCard(collection: CollectionModel, card: CardModel){
        var foundCollection: CollectionModel? = collections.find { c -> c.id == collection.id }
        if (foundCollection != null) {
            card.id = getNewCardId()
            foundCollection.cards.add(card)
            logCollections()
            logCards(foundCollection)
        }
    }
    // logs collections when a new collection is created
    fun logCollections() {
        collections.forEach{ i("$it") }
    }
    fun logCards(collection: CollectionModel){
        collection.cards.forEach{i("$it")}
    }
}