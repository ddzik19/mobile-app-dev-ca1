package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.addBtn.setOnClickListener() {
            i("add Button Pressed")
        }
    }
}