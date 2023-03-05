package ie.wit.ca1.models

interface CollectionStore {
    fun findAll(): List<CollectionModel>
    fun create(collection: CollectionModel)
    fun delete(collection: CollectionModel)
    fun update(collection: CollectionModel)
    fun findAllCards(collection: CollectionModel): List<CardModel>
    fun addCard(collection: CollectionModel, card: CardModel)
    fun deleteCard(card: CardModel)
    fun updateCard(card: CardModel)
}