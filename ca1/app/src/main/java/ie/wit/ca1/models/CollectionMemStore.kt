package ie.wit.ca1.models

import timber.log.Timber.i

class CollectionMemStore: CollectionStore {
    val collections = ArrayList<CollectionModel>()

    // returning all collections
    override fun findAll(): List<CollectionModel> {
        return collections
    }

    // creating/adding new collections
    override fun create(collection: CollectionModel) {
        collections.add(collection)
    }

    // logs collections when a new collection is created
    fun logCollections() {
        collections.forEach{ i("$it") }
    }
}