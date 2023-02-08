package ie.wit.ca1

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {
    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Collection App started")
    }
}