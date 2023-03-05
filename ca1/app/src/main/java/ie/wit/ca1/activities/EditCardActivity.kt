package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityEditCardBinding
import ie.wit.ca1.databinding.ActivityEditCollectionBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber

class EditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCardBinding
    private var card = CardModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)

        Timber.plant(Timber.DebugTree())
        Timber.i("Edit Card Activity Started :)")

        app = application as MainApp
        binding = ActivityEditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        if (intent.hasExtra("edit_card")) {
            card = intent.getParcelableExtra("edit_card",CardModel::class.java)!!
        }

        binding.updateCardBtn.setOnClickListener{
            card.cardName = binding.nameInput.text.toString()
            card.cardRarity = binding.raritySpinner.selectedItem.toString()
            card.cardType = binding.typeSpinner.selectedItem.toString()
            card.cardNumber = binding.cardNumberInput.text.toString()
            card.isCollected = binding.isCollectedBtn.isChecked

            if(card.cardName.isNotEmpty() && card.cardNumber.isNotEmpty()){
                app.collections.updateCard(card)
                Timber.i("Updated Card; $card")
                setResult(RESULT_OK)
                finish()
            }else{
                Snackbar
                    .make(
                        it,
                        "Please enter a new title and card number!",
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
        }
    }
}