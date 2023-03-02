package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityAddCardBinding
import ie.wit.ca1.databinding.CreateCollectionActivityBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCardBinding
    var card = CardModel()
    var collection = CollectionModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        Timber.plant(Timber.DebugTree())
        Timber.i("Add Card Activity started :)")

        app = application as MainApp
        binding = ActivityAddCardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val raritySpinner = findViewById<Spinner>(ie.wit.ca1.R.id.raritySpinner)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                ie.wit.ca1.R.array.rarities,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        raritySpinner.adapter = adapter

        binding.addCardBtn.setOnClickListener {
            card.cardName = binding.nameInput.text.toString()
            card.cardNumber = binding.cardNumberInput.text.toString()
            card.cardRarity = binding.raritySpinner.selectedItem.toString()
            card.isCollected = binding.isCollectedBtn.isChecked

            if (card.cardName.isNotEmpty() && card.cardRarity.isNotEmpty() && card.cardNumber.isNotEmpty()) {
                app.collections.addCard(collection.copy(), card.copy())
                Timber.i("Added new card ${card.copy()} to ${collection.copy()}")
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