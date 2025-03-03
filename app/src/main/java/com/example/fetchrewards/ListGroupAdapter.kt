import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.data.Item

// Adapter for displaying items grouped by list ID in an expandable format

class ListGroupAdapter(private var groupedItems: Map<Int, List<Item>>) :
    RecyclerView.Adapter<ListGroupAdapter.GroupViewHolder>() {

    private val listIds = groupedItems.keys.sorted()

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listIdTextView: TextView = view.findViewById(R.id.list_id_header)
        val itemsRecyclerView: RecyclerView = view.findViewById(R.id.items_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_group_row, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val listId = listIds[position]
        val items = groupedItems[listId] ?: emptyList()

        holder.listIdTextView.text = "List ID: $listId"

        // Use existing adapter if possible (prevents unnecessary recreation)

        val itemAdapter = ItemAdapter(items)
        holder.itemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = itemAdapter
        }
    }

    override fun getItemCount() = listIds.size

    //Updates the dataset and notifies the adapter to refresh

    fun updateGroups(newGroupedItems: Map<Int, List<Item>>) {
        groupedItems = newGroupedItems
        listIds.toMutableList().clear()
        listIds.toMutableList().addAll(newGroupedItems.keys.sorted())
        notifyDataSetChanged()
    }
}