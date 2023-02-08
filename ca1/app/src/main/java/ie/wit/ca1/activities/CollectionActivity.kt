package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.CollectionActivityBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import timber.log.Timber.i

class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: CollectionActivityBinding
    var collection = CollectionModel()
    lateinit var app : MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_activity)

        Timber.plant(Timber.DebugTree())
        i("Collection Activity started :)")

        app = application as MainApp
        binding = CollectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
             collection.title = binding.titleInput.text.toString()
            if (collection.title.isNotEmpty()) {
                app.collections.add(collection.copy())
                i("Added new collection; $collection")
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title for the collection :)", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}