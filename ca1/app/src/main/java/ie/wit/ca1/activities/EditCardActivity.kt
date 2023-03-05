package ie.wit.ca1.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityEditCardBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import timber.log.Timber

class EditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCardBinding
    private var card = CardModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())
        Timber.i("Edit Card Activity Started :)")

        binding = ActivityEditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val typeSpinner = findViewById<Spinner>(R.id.typeSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

        val raritySpinner = findViewById<Spinner>(R.id.raritySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.rarities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            raritySpinner.adapter = adapter
        }

        if (intent.hasExtra("edit_card")) {
            card = intent.getParcelableExtra("edit_card", CardModel::class.java)!!
            binding.nameInput.setText(card.cardName)
            binding.raritySpinner.setSelection(getIndex(raritySpinner, card.cardRarity))
            binding.typeSpinner.setSelection(getIndex(typeSpinner, card.cardType))
            binding.cardNumberInput.setText(card.cardNumber)
            binding.isCollectedBtn.isChecked = card.isCollected
        }

        binding.updateCardBtn.setOnClickListener {
            val cardName = binding.nameInput.text.toString().trim()
            val cardNumber = binding.cardNumberInput.text.toString().trim()

            if (cardName.isEmpty() || cardNumber.isEmpty()) {
                Snackbar.make(
                    it,
                    "Please enter a card name and number",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                card.cardName = cardName
                card.cardRarity = binding.raritySpinner.selectedItem.toString()
                card.cardType = binding.typeSpinner.selectedItem.toString()
                card.cardNumber = cardNumber
                card.isCollected = binding.isCollectedBtn.isChecked

                app.collections.updateCard(card)
                Timber.i("Updated Card; $card")
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun getIndex(spinner: Spinner, value: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                return i
            }
        }
        return 0
    }
}