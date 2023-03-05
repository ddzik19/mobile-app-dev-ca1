package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityAddCardBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber
import java.util.regex.Pattern

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

        if (intent.hasExtra("create_card_activity")) {
            collection = intent.getParcelableExtra("create_card_activity", CollectionModel::class.java)!!
            Timber.i("Collection: $collection")
        } else {
            Timber.e("Error: no collection found in intent extras")
            finish() // close the activity
            return // stop executing further code in this method
        }

        val typeSpinner = findViewById<Spinner>(R.id.typeSpinner)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.types,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        typeSpinner.adapter = adapter

        val raritySpinner = findViewById<Spinner>(R.id.raritySpinner)
        val adapter2 =
            ArrayAdapter.createFromResource(
                this,
                R.array.rarities,
                android.R.layout.simple_spinner_item
            )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item)
        raritySpinner.adapter = adapter2

        binding.addCardBtn.setOnClickListener {
            // Validate card name
            val name = binding.nameInput.text.toString().trim()
            if (name.isEmpty()) {
                Snackbar
                    .make(
                        it,
                        "Please enter a name for the card",
                        Snackbar.LENGTH_LONG
                    )
                    .show()
//                returning @setOnClickListener means that the lambda function has successfully executed
                return@setOnClickListener
            }
            if (!isValidCardName(name)) {
                Snackbar
                    .make(
                        it,
                        "Card name can only contain letters and spaces",
                        Snackbar.LENGTH_LONG
                    )
                    .show()
                return@setOnClickListener
            }
            card.cardName = name

            // Validate card number
            val number = binding.cardNumberInput.text.toString().trim()
            if (number.isEmpty()) {
                Snackbar
                    .make(
                        it,
                        "Please enter a number for the card",
                        Snackbar.LENGTH_LONG
                    )
                    .show()
                return@setOnClickListener
            }
            card.cardNumber = number
            card.cardRarity = binding.raritySpinner.selectedItem.toString()
            card.cardType = binding.typeSpinner.selectedItem.toString()
            card.collectionId = collection.id
            card.isCollected = binding.isCollectedBtn.isChecked

            app.collections.addCard(collection.copy(), card.copy())
            Timber.i("Added new card ${card.copy()} to ${collection.copy()}")
            setResult(RESULT_OK)
            finish()
        }
    }

//    making sure the cardName only contains letters
    private fun isValidCardName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z ]+\$")
        return pattern.matcher(name).matches()
    }
}