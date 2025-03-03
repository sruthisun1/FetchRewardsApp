import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.data.Item

// Adapter for displaying a list of items in a RecyclerView

// list of items to display
class ItemAdapter(private var items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        // ViewHolder that holds references to item views
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_name)
        val idTextView: TextView = view.findViewById(R.id.item_id)
    }

        //Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }
        //Binds item data to the ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name ?: "Unknown"
        holder.idTextView.text = item.id.toString()
    }

        //Returns the total number of items in the list
    override fun getItemCount() = items.size

        //Updates the adapter's data and refreshes the RecyclerView efficiently
    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}