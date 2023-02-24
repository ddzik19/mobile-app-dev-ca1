package ie.wit.ca1.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        // https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860
        // here i found steps on how to implement the spinner but now how to retrieve information from it.
        val genreSpinner = findViewById<Spinner>(ie.wit.ca1.R.id.genreSpinner)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                ie.wit.ca1.R.array.genres,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        genreSpinner.adapter = adapter;

        binding.updateBtn.setOnClickListener {
            collection.title = binding.titleInput.text.toString()
            collection.genre = binding.genreSpinner.selectedItem.toString()

            if (collection.title.isNotEmpty() && collection.title.isNotEmpty()) {
                app.collections.create(collection.copy())
                i("Added new collection; $collection")
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar
                    .make(
                        it,
                        "Please Enter a title for the collection :)",
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
        }
    }
}