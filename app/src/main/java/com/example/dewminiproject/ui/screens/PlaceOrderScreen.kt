package com.example.dewminiproject.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dewminiproject.data.local.entity.Order
import com.example.dewminiproject.data.local.entity.Product
import com.example.dewminiproject.navigation.Screen
import com.example.dewminiproject.viewmodel.MainViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrderScreen(navController: NavController, viewModel: MainViewModel, productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("1") }
    var deliveryMethod by remember { mutableStateOf("Pickup") }
    var address by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    
    // Spec selections
    var selectedSize by remember { mutableStateOf("") }
    var selectedMaterial by remember { mutableStateOf("") }

    val currentUser by viewModel.currentUser.collectAsState()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedFileUri = uri }

    LaunchedEffect(productId) {
        product = viewModel.getProductById(productId)
    }
    
    // Set initial specs when product loads
    LaunchedEffect(product) {
        product?.let {
            selectedSize = it.availableSizes.split(",").firstOrNull()?.trim() ?: ""
            selectedMaterial = it.availableMaterials.split(",").firstOrNull()?.trim() ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Specifications") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        product?.let { p ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = p.name, 
                    style = MaterialTheme.typography.headlineMedium, 
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Configure your order details", 
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                // SIZE SELECTION
                SpecDropdown(label = "Select Size", options = p.availableSizes.split(","), selected = selectedSize) { selectedSize = it }
                Spacer(modifier = Modifier.height(16.dp))

                // MATERIAL SELECTION
                SpecDropdown(label = "Paper / Material Type", options = p.availableMaterials.split(","), selected = selectedMaterial) { selectedMaterial = it }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { if (it.all { char -> char.isDigit() }) quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Delivery Option", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = deliveryMethod == "Pickup", onClick = { deliveryMethod = "Pickup" })
                    Text("Store Pickup", modifier = Modifier.clickable { deliveryMethod = "Pickup" }, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(24.dp))
                    RadioButton(selected = deliveryMethod == "Delivery", onClick = { deliveryMethod = "Delivery" })
                    Text("Home Delivery", modifier = Modifier.clickable { deliveryMethod = "Delivery" }, style = MaterialTheme.typography.bodyLarge)
                }

                if (deliveryMethod == "Delivery") {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Delivery Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = { filePickerLauncher.launch("*/*") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedFileUri == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, tint = if (selectedFileUri == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (selectedFileUri == null) "Upload Artwork" else "Artwork Attached",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = if (selectedFileUri == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Custom Text / Instructions") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = MaterialTheme.shapes.large
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                val qtyValue = quantity.toIntOrNull() ?: 0
                val totalPrice = qtyValue * p.basePrice
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                ) {
                    Row(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Total Amount", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        Text(text = "LKR ${String.format(Locale.getDefault(), "%.2f", totalPrice)}", style = MaterialTheme.typography.headlineSmall, fontWeight = androidx.compose.ui.text.font.FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val order = Order(
                            userId = currentUser?.id ?: 0,
                            productType = p.name,
                            selectedSize = selectedSize,
                            selectedMaterial = selectedMaterial,
                            quantity = qtyValue,
                            totalPrice = totalPrice,
                            deliveryMethod = deliveryMethod,
                            deliveryAddress = if (deliveryMethod == "Delivery") address else "Store Pickup",
                            status = "Processing",
                            customInstructions = instructions,
                            designFileUri = selectedFileUri?.toString() ?: ""
                        )
                        viewModel.placeOrder(order)
                        navController.navigate(Screen.OrderHistory.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = qtyValue > 0 && selectedSize.isNotEmpty() && selectedMaterial.isNotEmpty()
                ) {
                    Text("Confirm & Place Order", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecDropdown(label: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.trim()) },
                    onClick = {
                        onSelect(selectionOption.trim())
                        expanded = false
                    }
                )
            }
        }
    }
}
