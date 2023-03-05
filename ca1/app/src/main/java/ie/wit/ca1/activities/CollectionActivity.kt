package ie.wit.ca1.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.ca1.R
import ie.wit.ca1.databinding.ActivityCollectionBinding
import ie.wit.ca1.databinding.CardWidgetBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber

class CollectionActivity : AppCompatActivity(), EditCardListener, DeleteCardListener {

    private lateinit var binding: ActivityCollectionBinding
    var collection = CollectionModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.collectionToolbar)

        Timber.plant(Timber.DebugTree())
        Timber.i("Collection Activity started :)")

        app = application as MainApp

        if (intent.hasExtra("collection_activity")) {
            collection = intent.getParcelableExtra("collection_activity", CollectionModel::class.java)!!
            Timber.i("Collection: $collection")
        } else {
            Timber.e("Error: no collection found in intent extras")
            finish() // close the activity
            return // stop executing further code in this method
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CardAdapter(app.collections.findAllCards(collection), this, this)

        if (binding.recyclerView.adapter == null) {
            Timber.e("Error: CardAdapter not set")
            finish() // close the activity
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AddCardActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        when (item.itemId){
            R.id.goBackBtn -> {
                val launcherIntent = Intent(this, CollectionListActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.collections.findAllCards(collection).size)
                }
            }

    // moves us to the edit activity for collection
    override fun onCardEditClick(card: CardModel){
        val launcherIntent = Intent(this, EditCardActivity::class.java)
        launcherIntent.putExtra("edit_card", card)
        getResult.launch(launcherIntent)
    }

    override fun onCardDeleteClick(card: CardModel){
        app.collections.deleteCard(card)
        val launcherIntent = Intent(this, CollectionActivity::class.java)
        launcherIntent.putExtra("collection_activity", collection)
        getResult.launch(launcherIntent)
    }
}


interface EditCardListener {
    fun onCardEditClick(card: CardModel)
}

interface DeleteCardListener {
    fun onCardDeleteClick(card: CardModel)
}
class CardAdapter constructor(private var cards: List<CardModel>, private val editListener: EditCardListener, private val deleteListener: DeleteCardListener) :
    RecyclerView.Adapter<CardAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val card = cards[holder.adapterPosition]
        holder.bind(card, editListener, deleteListener)
    }

    override fun getItemCount(): Int = cards.size

    // setting the information to be displayed
    class MainHolder(private val binding : CardWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            card: CardModel,
            editListener: EditCardListener,
            deleteListener: DeleteCardListener
        ) {
            binding.cardName.text = card.cardName
            binding.cardNumber.text = "N: ${card.cardNumber}"
            binding.cardRarity.text = "R: ${card.cardRarity}"
            binding.isCollected.text = "C: ${card.isCollected}"
            binding.cardType.text = "T: ${card.cardType}"
            binding.editCardBtn.setOnClickListener{editListener.onCardEditClick(card)}
            binding.deleteCardBtn.setOnClickListener{deleteListener.onCardDeleteClick(card)}
        }
    }
}