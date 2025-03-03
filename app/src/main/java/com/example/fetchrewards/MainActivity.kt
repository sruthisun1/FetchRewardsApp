package com.example.fetchrewards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchrewards.data.Item
import com.example.fetchrewards.ui.ItemViewModel
import com.example.fetchrewards.ui.theme.FetchRewardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchRewardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF2C2C54)
                ) {
                    FetchApp()
                }
            }
        }
    }
}

// Main app UI that manages different loading states

@Composable
fun FetchApp(viewModel: ItemViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ItemViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C54)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFF8500))
            }
        }
        is ItemViewModel.UiState.Success -> {
            ItemListScreen(groupedItems = state.groupedItems)
        }
        is ItemViewModel.UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C54)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = Color.White // White for contrast
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadItems() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8500))
                    ) {
                        Text("Retry", color = Color.White)
                    }
                }
            }
        }

        else -> {}
    }
}

// Displays a list of items grouped by listId

@Composable
fun ItemListScreen(groupedItems: Map<Int, List<Item>>) {
    val expandedState = remember { mutableStateMapOf<Int, Boolean>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Fetch Rewards Item List",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA447), //orange
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        groupedItems.keys.sorted().forEach { listId ->
            item {
                ExpandableListSection(
                    listId = listId,
                    items = groupedItems[listId] ?: emptyList(),
                    isExpanded = expandedState[listId] ?: false,
                    onToggle = { expandedState[listId] = !(expandedState[listId] ?: false) }
                )
            }
        }
    }
}

// Creates an expandable button for each List ID group

@Composable
fun ExpandableListSection(
    listId: Int,
    items: List<Item>,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C2C54))
            .padding(8.dp)
    ) {
        Button(
            onClick = onToggle,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A3A6E)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "List ID: $listId",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2C2C54))
                    .padding(8.dp)
            ) {
                items.forEach { item ->
                    ItemCard(item = item)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

// Individual item card displaying Name and ID side by side

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(text = "Name: ", fontWeight = FontWeight.Bold)
                Text(text = item.name ?: "Unknown")
            }
            Row {
                Text(text = "ID: ", fontWeight = FontWeight.Bold)
                Text(text = item.id.toString())
            }
        }
    }
}


