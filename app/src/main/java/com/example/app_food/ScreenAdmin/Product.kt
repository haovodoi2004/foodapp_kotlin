package com.example.app_food.ScreenAdmin

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel

@Composable
fun Product(
    onButtonClick: (String) -> Unit,
    productViewModel: ProViewModel,
    protypeViewModel: ProtypeViewModel,
) {
    val listProtype by protypeViewModel.protypeItems.observeAsState(initial = emptyList())
    val listProduct by productViewModel.Product.observeAsState(initial = emptyList())
    var selectedCategory by remember { mutableStateOf("") }
    var filteredProducts by remember { mutableStateOf(listProduct) } // Danh sách đã lọc
    var showDialog by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }
    var visibleProducts by remember { mutableStateOf(emptyList<Product>()) }
    var produc = Product(
        id = "",
        name = "",
        price = 0,
        avatar = "",
        infor = "",
        category = "",
        quantity = 0,
        status = 0
    )
    // Fetch dữ liệu khi cần
//    LaunchedEffect(listProduct) {
//        if (listProduct.isEmpty()) productViewModel.fetchProduct()
//        if (listProtype.isEmpty()) protypeViewModel.fetchProtype()
//    }
//    LaunchedEffect(listProduct) {
//        filteredProducts = if (selectedCategory.isEmpty()) {
//            productViewModel.fetchProduct()
//            listProduct
//        } else {
//            listProduct.filter { it.category == selectedCategory }
//        }
//    }
//
//    // Lọc danh sách sản phẩm theo danh mục
//    LaunchedEffect(selectedCategory, listProduct) {
//        filteredProducts = if (selectedCategory.isEmpty()) {
//            listProduct
//        } else {
//            listProduct.filter { it.category == selectedCategory }
//        }
//    }

    LaunchedEffect(Unit) {
        if (listProduct.isEmpty()) {
            productViewModel.fetchProduct()
        }
        if (listProtype.isEmpty()) {
            protypeViewModel.fetchProtype()
        }
    }

    LaunchedEffect(selectedCategory, listProduct) {
        filteredProducts = if (selectedCategory.isEmpty()) {
            listProduct
        } else {
            listProduct.filter { it.category == selectedCategory
            }
        }
    }

    LaunchedEffect(filteredProducts) {
        visibleProducts = filteredProducts.filter { it.status.toInt() == 0 }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Giao diện danh mục
            dropMenu(
                listProtype = listProtype,
                selectedCategory = selectedCategory,
                onCategorySelected = { category -> selectedCategory = category }
            )

            // Danh sách sản phẩm
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
//                    .weight(1f) // Để LazyColumn tự điều chỉnh chiều cao
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color.White),


            ) {
                items(visibleProducts, key = { it.id!! }) { product ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            Log.d("jfaoijoejg","$it")
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                showDialog = true
                                selectedId = product.id!!
                                produc = product
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(text = "Delete", color = Color.White, fontSize = 16.sp)
                            }
                        })
                        {
                        if(product.status.toInt()==0) {
                            // Nội dung chính của item
                            productItem(product = product, onButton = onButtonClick, listProtype)
                        }

                        }

                }
            }

        }

        // Nút thêm sản phẩm
        IconButton(
            onClick = { show = true },
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp)
                .background(Color(0xFF673AB7), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Product",
                tint = Color.White
            )
        }

        // Dialog xác nhận xóa
        if (showDialog) {
            productDelete(
                onDismiss = { showDialog = false },
                onConfirm = {

                    val pro = Product(
                        selectedId,
                        produc.name,
                        produc.price,
                        produc.avatar,
                        produc.infor,
                        produc.category,
                        produc.quantity,
                        1
                    )
                    productViewModel.updateProduct(selectedId, pro)


                    showDialog = false
                }
            )
        }

        if (show) {
            productAdd(
                onDismiss = { show = false },
                listProtype = listProtype,
                productViewModel = ProViewModel()
            )
        }
    }
}

@Composable
fun productAdd(onDismiss : ()-> Unit,listProtype : List<Protype>,productViewModel: ProViewModel){
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0) }
    var avatar by remember { mutableStateOf("") }
    var infor by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Thêm sản phẩm")
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Tên sản phẩm") })
                OutlinedTextField(
                    value = price.toString(),
                    onValueChange = { price = it.toIntOrNull() ?: price },
                    label = { Text(text = "Giá sản phẩm") })
                DropMenu(listProtype = listProtype, selectedCategory = selectedCategory, onCategorySelected = {selectedCategory=it})
                OutlinedTextField(
                    value = avatar,
                    onValueChange = { avatar = it },
                    label = { Text(text = "Link ảnh") },
                    maxLines = 3)
                OutlinedTextField(
                    value = infor,
                    onValueChange = { infor = it },
                    label = { Text(text = "Thông tin sản phẩm") },
                    maxLines = 3)
                OutlinedTextField(
                    value = quantity.toString(),
                    onValueChange = { quantity = it.toIntOrNull()?:quantity },
                    label = { Text(text = "Số lượng") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val pro = Product("",name,price,avatar,infor,selectedCategory,quantity,0)
                productViewModel.addProduct(pro)
                onDismiss()
            }) {
                Text(text = "Kê")
            }
        },
        dismissButton = {
            Button(onClick = {onDismiss()}) {
                Text(text = "Hủy")
            }
        })
}

@Composable
fun productDelete(onDismiss : ()-> Unit,onConfirm : ()-> Unit){
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "THông báo") },
        text = { Text(text = "Bạn có muốn xóa ko") },
        dismissButton = {
            Button(onClick = {onDismiss()}) {
                Text(text = "Hủy")
            }
        },
        confirmButton = {
            Button(onClick = {onConfirm()}) {
                Text(text = "Ok")
            }
        })
}

@Composable
fun productItem(product: Product,onButton : (String) -> Unit,list : List<Protype>){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onButton(product.id!!) },
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.avatar,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = product.name ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CategoryItems(title: String,onSelect : (String) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelect(title) }) {
        Text(text = title, fontSize = 16.sp)
    }
}

@Composable
fun dropMenu(
    listProtype: List<Protype>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedCategory) }

    Box(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            label = { Text(text = "Danh mục") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth() // Đặt chiều rộng tự động
                .heightIn(max = 300.dp) // Giới hạn chiều cao tối đa
                .clip(MaterialTheme.shapes.medium) // Bo góc
        ) {
            listProtype.forEach { protype ->
                Box(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = protype.name,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                            )
                        },
                        onClick = {
                            selectedText = protype.name
                            onCategorySelected(protype.name)
                            expanded = false
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp) // Khoảng cách giữa các mục

                    )
                }
            }
        }
    }
}
