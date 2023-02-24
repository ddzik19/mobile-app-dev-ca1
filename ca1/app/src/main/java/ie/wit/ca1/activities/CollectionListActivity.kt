package ie.wit.ca1.activities

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
import ie.wit.ca1.R
import androidx.recyclerview.widget.RecyclerView
import ie.wit.ca1.databinding.CollectionListActivityBinding
import ie.wit.ca1.databinding.CollectionWidgetBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel

class CollectionListActivity : AppCompatActivity(),CollectionListener, EditListener {

    lateinit var app: MainApp
    private lateinit var binding: CollectionListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CollectionListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CollectionAdapter(app.collections.findAll(),this,this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CollectionActivity::class.java)
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

    override fun onCollectionClick(collection: CollectionModel) {
        val launcherIntent = Intent(this, CollectionActivity::class.java)
        launcherIntent.putExtra("collection_items", collection)
        getClickResult.launch(launcherIntent)
    }

    // moves us to the edit activity for collection
    override fun onCollectionEditClick(collection: CollectionModel){
        val launcherIntent = Intent(this, EditCollectionActivity::class.java)
        launcherIntent.putExtra("edit_collection", collection)
        getClickResult.launch(launcherIntent)
    }

    // checking if we can move to activity
    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.collections.findAll().size)
            }
        }
}

// edit button click
interface EditListener {
    fun onCollectionEditClick(collection: CollectionModel)
}
// collection click
interface CollectionListener {
    fun onCollectionClick(collection: CollectionModel)
}

class CollectionAdapter constructor(private var collections: List<CollectionModel>, private val listener: CollectionListener, private val editListener: EditListener) :
    RecyclerView.Adapter<CollectionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CollectionWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val collection = collections[holder.adapterPosition]
        holder.bind(collection,listener,editListener)
    }

    override fun getItemCount(): Int = collections.size

    // setting the information to be displayed
    class MainHolder(private val binding : CollectionWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: CollectionModel, clickListener: CollectionListener, editListener: EditListener) {
            binding.collectionTitle.text = collection.title
            binding.genreText.text = collection.genre
            binding.root.setOnClickListener{clickListener.onCollectionClick(collection)}
            binding.editBtn.setOnClickListener{editListener.onCollectionEditClick(collection)}
        }
    }
}