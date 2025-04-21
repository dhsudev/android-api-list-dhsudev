package com.example.apilist.ui.utils.reusableitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apilist.viewmodel.ListApiViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MagicSearchBar(
    vm: ListApiViewModel,
    navigateToDetail: (String) -> Unit
) {
    val query = vm.searchQuery
    val suggestions = vm.searchSuggestions
    var active by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    SearchBar(
        query = query,
        onQueryChange = { vm.onSearchQueryChanged(it) },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search cards...", style = MaterialTheme.typography.titleLarge)  },
        leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        suggestions.forEach { suggestion ->
            Text(
                text = suggestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        active = false
                        vm.searchQuery = ""
                        vm.searchSuggestions = emptyList()
                        vm.onSuggestionClicked(suggestion, navigateToDetail)
                    }
                    .padding(16.dp)
            )
        }
    }
}