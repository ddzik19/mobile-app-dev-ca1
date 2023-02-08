package ie.wit.ca1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.ca1.R
import androidx.recyclerview.widget.RecyclerView
import ie.wit.ca1.databinding.CollectionListActivityBinding
import ie.wit.ca1.databinding.CollectionWidgetBinding
import ie.wit.ca1.main.MainApp
import ie.wit.ca1.models.CollectionModel

class CollectionListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: CollectionListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_list_activity)

        binding = CollectionListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CollectionAdapter(app.collections)
    }
}

class CollectionAdapter constructor(private var collections: List<CollectionModel>) :
    RecyclerView.Adapter<CollectionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CollectionWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val collection = collections[holder.adapterPosition]
        holder.bind(collection)
    }

    override fun getItemCount(): Int = collections.size

    class MainHolder(private val binding : CollectionWidgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placemark: CollectionModel) {
            binding.collectionTitle.text = placemark.title
        }
    }
}