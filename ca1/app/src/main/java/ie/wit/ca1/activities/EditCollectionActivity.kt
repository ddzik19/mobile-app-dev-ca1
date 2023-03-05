package ie.wit.ca1.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityEditCollectionBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import timber.log.Timber.i

class EditCollectionActivity : AppCompatActivity() {

    // define the view binding
    private lateinit var binding: ActivityEditCollectionBinding

    // create a new collection object
    private var collection = CollectionModel()

    // declare the app object
    private lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the view using view binding
        binding = ActivityEditCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // plant a debug tree for logging
        Timber.plant(Timber.DebugTree())
        i("Edit Collection Activity Started :)")

        // initialize the app object
        app = application as MainApp

        // set up the genre spinner
        val genreSpinner = binding.genreSpinner
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreSpinner.adapter = adapter

        // retrieve the collection to edit from the intent, if available
        if (intent.hasExtra("edit_collection")) {
            collection = intent.getParcelableExtra("edit_collection", CollectionModel::class.java)!!
            binding.titleInput.setText(collection.title)
            genreSpinner.setSelection(adapter.getPosition(collection.genre))
        }

        // set up the update button click listener
        binding.updateBtn.setOnClickListener {
            val newTitle = binding.titleInput.text.toString().trim()
            val newGenre = binding.genreSpinner.selectedItem.toString()

            if (newTitle.isEmpty()) {
                Snackbar.make(it, "Please enter a title for the collection", Snackbar.LENGTH_SHORT).show()
            } else if (newTitle == collection.title && newGenre == collection.genre) {
                Snackbar.make(it, "No changes were made to the collection", Snackbar.LENGTH_SHORT).show()
            } else {
                collection.title = newTitle
                collection.genre = newGenre
                app.collections.update(collection)
                i("Updated Collection; $collection")
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}