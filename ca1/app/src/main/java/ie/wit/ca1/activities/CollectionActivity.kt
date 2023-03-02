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
import ie.wit.ca1.databinding.CollectionWidgetBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CardModel
import ie.wit.ca1.models.CollectionModel
import timber.log.Timber

class CollectionActivity : AppCompatActivity() {

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

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = app.collections.findAllCards(collection)
            ?.let { CardAdapter(it,) }

        if (intent.hasExtra("collection_activity")) {
            collection = intent.extras?.getParcelable("collection_activity")!!
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
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.collections.findAll().size)
            }
        }
}

class CardAdapter constructor(private var cards: List<CardModel>) :
    RecyclerView.Adapter<CardAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val card = cards[holder.adapterPosition]
        holder.bind(card)
    }

    override fun getItemCount(): Int = cards.size

    // setting the information to be displayed
    class MainHolder(private val binding : CardWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(card: CardModel) {
            binding.cardName.text = card.cardName
            binding.cardNumber.text = "N: ${card.cardNumber}"
            binding.cardRarity.text = "R: ${card.cardRarity}"
            binding.isCollected.text = "C: ${card.isCollected}"
//            binding.root.setOnClickListener{clickListener.onCollectionClick(collection)}
//            binding.editBtn.setOnClickListener{editListener.onCollectionEditClick(collection)}
//            binding.deleteBtn.setOnClickListener{deleteListener.onCollectionDeleteClick(collection)}
        }
    }
}