package com.example.fetchrewards.data

class ItemRepository(private val apiService: FetchApiService = FetchApiService.create()) {

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


    fun getItemsGroupedByListId(items: List<Item>): Map<Int, List<Item>> {
        return items.groupBy { it.listId }
    }
}