package com.example.app_food.ScreenAdmin

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel

@Composable
fun Product(
    onButtonClick: (String) -> Unit,
    protypeViewModel: ProtypeViewModel= ProtypeViewModel(),
    productViewModel: ProViewModel=ProViewModel()
) {
    val listProtype by protypeViewModel.protypeItems.observeAsState(initial = emptyList())
    val listproduct by productViewModel.Product.observeAsState(initial = emptyList())
    var selectedCategory by remember { mutableStateOf("") }
    var filteredProducts by remember { mutableStateOf(listproduct) } // State để lưu danh sách đã lọc

    LaunchedEffect(Unit) {
        if(listproduct.isEmpty()||listproduct.isEmpty()){
            productViewModel.fetchProduct()
            protypeViewModel.fetchProtype()
        }

        println("Products: ${productViewModel.Product.value}")
        println("Protypes: ${protypeViewModel.protypeItems.value}")
    }

    // Lọc danh sách sản phẩm khi danh mục thay đổi
    LaunchedEffect(selectedCategory, listproduct) {
        filteredProducts = if (selectedCategory.isEmpty()) {
            listproduct
        } else {
            listproduct.filter { it.category == selectedCategory }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Giao diện chọn danh mục
            dropMenu(
                listProtype,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                }
            )
            // Hiển thị danh sách sản phẩm đã lọc
            listProduct(filteredProducts,onButtonClick)
        }
    }
}


@Composable
fun productItem(product: Product,onButton : (String) -> Unit){
    Card(modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp), onClick = {
        onButton(product.id)
    }) {
        Column() {
            // Bạn có thể thêm thông tin khác của sản phẩm tại đây
            AsyncImage(
                model = product.avatar,  // Đường dẫn URL của ảnh
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.15f), // Giới hạn chiều cao tối đa,  // Đảm bảo ảnh vuông, chiếm hết chiều rộng
                contentScale = ContentScale.Crop  // Để ảnh cắt vừa khung

            )
            Text(text = product.name ?: "Unknown", fontSize = 20.sp)
            Text(text = "$ ${product.price.toString()}")
        }
    }
}

@Composable
fun listProduct(producList : List<Product>,onButton : (String)->Unit){

    LazyColumn() {
        items(producList){
            pro->
            productItem(pro,onButton)
        }
    }
}

@Composable
fun CategoryItems(title: String,onSelect : (String) -> Unit){
    Row(modifier = Modifier.fillMaxWidth().clickable { onSelect(title) }) {
        Text(text = title, fontSize = 16.sp)
    }
}

@Composable
fun dropMenu(listProtype : List<Protype>,selectedCategory: String,
             onCategorySelected: (String) -> Unit, protypeViewModel: ProtypeViewModel = ProtypeViewModel(),
             productViewModel: ProViewModel = ProViewModel()){
    LaunchedEffect(Unit) {
        productViewModel.fetchProduct()
        protypeViewModel.fetchProtype()
    }
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
                        ).onGloballyPositioned { coordinates ->
                            textFliedSize=coordinates.size.toSize()

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