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

class CollectionListActivity : AppCompatActivity(), CollectionListener, EditListener, DeleteListener {

    private lateinit var app: MainApp
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
        binding.recyclerView.adapter = CollectionAdapter(app.collections.findAll(), this, this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                try {
                    val launcherIntent = Intent(this, CreateCollectionActivity::class.java)
                    getResult.launch(launcherIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.recyclerView.adapter?.notifyItemRangeChanged(0, app.collections.findAll().size)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun onCollectionClick(collection: CollectionModel) {
        try {
            val launcherIntent = Intent(this, CollectionActivity::class.java)
            launcherIntent.putExtra("collection_activity", collection)
            getResult.launch(launcherIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCollectionEditClick(collection: CollectionModel) {
        try {
            val launcherIntent = Intent(this, EditCollectionActivity::class.java)
            launcherIntent.putExtra("edit_collection", collection)
            getResult.launch(launcherIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCollectionDeleteClick(collection: CollectionModel) {
        try {
            app.collections.delete(collection)
            val launcherIntent = Intent(this, CollectionListActivity::class.java)
            getResult.launch(launcherIntent)
        } catch (e: Exception) {
            e.printStackTrace()
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
// delete collection
interface DeleteListener {
    fun onCollectionDeleteClick(collection: CollectionModel)
}

class CollectionAdapter constructor(private var collections: List<CollectionModel>, private val listener: CollectionListener, private val editListener: EditListener, private val deleteListener: DeleteListener) :
    RecyclerView.Adapter<CollectionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CollectionWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val collection = collections[holder.adapterPosition]
        holder.bind(collection,listener,editListener, deleteListener)
    }

    override fun getItemCount(): Int = collections.size

    // setting the information to be displayed
    class MainHolder(private val binding : CollectionWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: CollectionModel, clickListener: CollectionListener, editListener: EditListener, deleteListener: DeleteListener) {
            binding.collectionTitle.text = collection.title
            binding.genreText.text = collection.genre
            binding.root.setOnClickListener{clickListener.onCollectionClick(collection)}
            binding.editBtn.setOnClickListener{editListener.onCollectionEditClick(collection)}
            binding.deleteBtn.setOnClickListener{deleteListener.onCollectionDeleteClick(collection)}
        }
    }
}