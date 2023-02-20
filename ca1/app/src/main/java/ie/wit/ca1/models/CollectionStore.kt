package ie.wit.ca1.models

interface CollectionStore {
    fun findAll(): List<CollectionModel>
    fun create(collection: CollectionModel)
}