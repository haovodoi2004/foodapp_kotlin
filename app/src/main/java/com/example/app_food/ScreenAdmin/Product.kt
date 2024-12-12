package com.example.app_food.ScreenAdmin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
    LaunchedEffect(listProduct) {
        if (listProduct.isEmpty()) productViewModel.fetchProduct()
        if (listProtype.isEmpty()) protypeViewModel.fetchProtype()
    }

    // Lọc danh sách sản phẩm theo danh mục
    LaunchedEffect(selectedCategory, listProduct) {
        filteredProducts = if (selectedCategory.isEmpty()) {
            listProduct
        } else {
            listProduct.filter { it.category == selectedCategory }
        }
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
                    .weight(1f) // Để LazyColumn tự điều chỉnh chiều cao
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredProducts, key = { it.id!! }) { product ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                showDialog = true
                                selectedId = product.id!!
                                produc=product
                                false
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                                    .padding(16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(text = "Delete", color = Color.White, fontSize = 16.sp)
                            }
                        }
                    ) {
                        if(product.status.toInt()==0){
                            productItem(product = product, onButton = onButtonClick,listProtype)
                        }
                    }
                }
            }
        }

        // Nút thêm sản phẩm
        IconButton(
            onClick = { show=true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(Color(0xFF673AB7), shape = CircleShape)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product", tint = Color.White)
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

        if(show){
            productAdd(onDismiss = { show=false},listProtype=listProtype, productViewModel = ProViewModel())
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
                onDismiss()
                productViewModel.addProduct(pro)
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
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.White), onClick = {
        onButton(product.id)
    }) {
        Column() {
            // Bạn có thể thêm thông tin khác của sản phẩm tại đây
            AsyncImage(
                model = product.avatar,  // Đường dẫn URL của ảnh
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.15f), // Giới hạn chiều cao tối đa,  // Đảm bảo ảnh vuông, chiếm hết chiều rộng
                contentScale = ContentScale.Crop  // Để ảnh cắt vừa khung

            )
            Text(text = product.name ?: "Unknown", fontSize = 20.sp)
            Text(text = "$ ${product.price.toString()}")
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
fun dropMenu(listProtype : List<Protype>,selectedCategory: String,
             onCategorySelected: (String) -> Unit){
    var category by remember { mutableStateOf("") }
    val heightTextflied by remember { mutableStateOf(55.dp) }
    var textFliedSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Box() {
        Column(modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )) {
            Text(
                modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                text = "Category"
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextflied)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFliedSize = coordinates.size.toSize()

                        },
                        value = selectedCategory, // Hiển thị giá trị đã chọn
                        onValueChange = {
                            onCategorySelected(it) // Truyền giá trị đã chọn
                            expanded = true
                        },
                        colors = TextFieldDefaults.colors(
                            errorLeadingIconColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFliedSize.width.dp),
                        elevation = elevatedCardElevation(10.dp)
                    ) {
                        LazyColumn(modifier = Modifier.heightIn(max = 150.dp),) {
                            if (category.isNotEmpty()) {
                                items(listProtype.filter {
                                    it.name.lowercase() // Thay `name` bằng thuộc tính đúng của đối tượng
                                        .contains(category.lowercase()) || it.name.lowercase()
                                        .contains("other")
                                }.sortedBy { it.name }) {
                                    CategoryItems(it.name) { title ->
                                        onCategorySelected(title) // Truyền giá trị khi chọn
                                        expanded = false
                                    }
                                }
                            } else {
                                items(listProtype.sortedBy { it.name }) { // Sắp xếp theo thuộc tính `name`
                                    CategoryItems(title = it.name) { title ->
                                        onCategorySelected(title) // Truyền giá trị khi chọn
                                        expanded = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}