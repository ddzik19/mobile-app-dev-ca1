package ie.wit.ca1.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.ca1.R
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.databinding.ActivityEditCollectionBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import timber.log.Timber.i

class EditCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCollectionBinding
    var collection = CollectionModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_collection)

        Timber.plant(Timber.DebugTree())
        Timber.i("Edit Collection Activity started :)")

        app = application as MainApp
        binding = ActivityEditCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}