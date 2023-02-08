package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.CollectionActivityBinding
import timber.log.Timber
import timber.log.Timber.i

class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: CollectionActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_activity)

        Timber.plant(Timber.DebugTree())
        i("Collection Activity started :)")

        binding = CollectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            val collectionTitle = binding.titleInput.text.toString()
            if (collectionTitle.isNotEmpty()) {
                i("add Button Pressed: $collectionTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title for the collection :)", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}