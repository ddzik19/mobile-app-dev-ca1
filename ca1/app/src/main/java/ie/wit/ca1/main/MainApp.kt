package ie.wit.ca1.main

import android.app.Application
import ie.wit.ca1.models.CollectionMemStore
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {
    val collections = CollectionMemStore()
    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Collection App started")
    }
}