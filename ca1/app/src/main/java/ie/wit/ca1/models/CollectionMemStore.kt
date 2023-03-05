package ie.wit.ca1.models

import android.util.Log
import timber.log.Timber
import kotlin.random.Random

private var id = 0
private var cardId = 0

internal fun getNewId(): Int {
    return id++
}

internal fun getNewCardId(): Int {
    return cardId++
}

class CollectionMemStore : CollectionStore {
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
    override fun create(collection: CollectionModel) {
        collection.id = getNewId()
        collections.add(collection)
        logCollections()
    }

    override fun update(collection: CollectionModel) {
        collections.find { it.id == collection.id }?.apply {
            title = collection.title
            genre = collection.genre
            logCollections()
        }
    }

    override fun delete(collection: CollectionModel) {
        collections.remove(collection)
        logCollections()
    }

    override fun addCard(collection: CollectionModel, card: CardModel) {
        collections.find { it.id == collection.id }?.let {
            card.id = getNewCardId()
            it.cards.add(card)
            logCards(it)
        }
    }

    override fun deleteCard(card: CardModel) {
        collections.find { it.id == card.collectionId }?.let {
            it.cards.remove(card)
            logCards(it)
        }
    }

    override fun updateCard(card: CardModel) {
        collections.find { it.id == card.collectionId }?.cards?.find { it.id == card.id }?.apply {
            cardNumber = card.cardNumber
            cardName = card.cardName
            cardType = card.cardType
            cardRarity = card.cardRarity
            isCollected = card.isCollected
        }
        collections.find { it.id == card.collectionId }?.let {
            logCards(it)
        }
    }

    private fun logCollections() {
        collections.forEach { Timber.i("$it") }
    }

    private fun logCards(collection: CollectionModel) {
        collection.cards.forEach { Timber.i("$it") }
    }
}