package ie.wit.ca1.activities

import ie.wit.ca1.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.databinding.CreateCollectionActivityBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import timber.log.Timber.i

class CreateCollectionActivity : AppCompatActivity() {
    private lateinit var binding: CreateCollectionActivityBinding
    var collection = CollectionModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ie.wit.ca1.R.layout.create_collection_activity)

        Timber.plant(Timber.DebugTree())
        i("Create Collection Activity started :)")

        app = application as MainApp
        binding = CreateCollectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860
        // here i found steps on how to implement the spinner but now how to retrieve information from it.
        val genreSpinner = findViewById<Spinner>(ie.wit.ca1.R.id.genreSpinner)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        genreSpinner.adapter = adapter

        binding.addBtn.setOnClickListener {
            collection.title = binding.titleInput.text.toString()
            collection.genre = binding.genreSpinner.selectedItem.toString()

            if (collection.title.isNotEmpty() && collection.genre.isNotEmpty()) {
                app.collections.create(collection.copy())
                i("Added new collection; ${collection.copy()}")
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
        binding.cancelBtn.setOnClickListener{
            finish()
        }
    }
}