package com.example.fetchrewards.data

class ItemRepository(private val apiService: FetchApiService = FetchApiService.create()) {

    // Fetches items from the API, filters out empty names, and sorts them
    suspend fun getItems(): List<Item> {
        val items = apiService.getItems()

        return items.filter { !it.name.isNullOrBlank() }
            .sortedWith(
                compareBy<Item> { it.listId }
                    .thenBy {
                        it.name?.replace("Item ", "")?.toIntOrNull() ?: Int.MAX_VALUE
                    }
            )
    }

    // Groups items by their listId.
    fun getItemsGroupedByListId(items: List<Item>): Map<Int, List<Item>> {
        return items.groupBy { it.listId }
    }
}