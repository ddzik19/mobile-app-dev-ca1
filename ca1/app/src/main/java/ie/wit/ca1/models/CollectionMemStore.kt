package ie.wit.ca1.models

import timber.log.Timber.i
import kotlin.random.Random

var id = 0

internal fun getNewId(): Int {
    return id++
}

class CollectionMemStore: CollectionStore {
    val collections = ArrayList<CollectionModel>()

    // returning all collections
    override fun findAll(): List<CollectionModel> {
        return collections
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

    // logs collections when a new collection is created
    fun logCollections() {
        collections.forEach{ i("$it") }
    }
}