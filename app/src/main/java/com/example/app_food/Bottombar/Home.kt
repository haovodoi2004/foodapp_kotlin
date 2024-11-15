@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.app_food.Bottombar


import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
<<<<<<< HEAD
=======
import androidx.navigation.NavController
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
import coil.compose.AsyncImage
import com.example.app_food.Model.New
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.R
import com.example.app_food.ViewModel.NewViewModel
import com.example.app_food.ViewModel.ProViewModel
import com.example.app_food.ViewModel.ProtypeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
<<<<<<< HEAD
fun Home(onItemproclick:()->Unit) {
=======
fun Home(navController: NavController) {
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item { Search() }
        item { AutoImage() }
<<<<<<< HEAD
        item { MainSc(onItemproclick = onItemproclick) }
=======
        item { ProtypeList(navController) }
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
        item { Even() }
    }
}

@Composable
fun NewItem(new: New?) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    if (new != null) {
        Row(
            modifier = Modifier
                .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                model = new.avatar ?: "",  // Kiểm tra null cho avatar
                contentDescription = new.title ?: "No title",  // Kiểm tra null cho title
                modifier = Modifier
                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.2f)
                    .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.08f).clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.6f)
            ) {
                Text(text = new.title ?: "No title")  // Kiểm tra null cho title
                Text(
                    text = new.content ?: "No content", maxLines = 2,  // Giới hạn tối đa 2 dòng
                    overflow = TextOverflow.Ellipsis, color = Color.Gray
                )  // Kiểm tra null cho content
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.rightarrow),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                )
            }
        }
    } else {
        Text("No data available")
    }
}

@Composable
fun Even(viewModel: NewViewModel = NewViewModel()) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Up Coming Even",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        )
        TextButton(onClick = {}) {
            Text(
                text = "See all",
                modifier = Modifier.padding(top = 1.dp, start = 8.dp),
                color = Color.LightGray,
                fontSize = 17.sp
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchNew()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.3f)
    ) { // Giới hạn chiều cao tối đa) {
        items(viewModel.NewItems) { newitem -> NewItem(newitem) }
    }
}


<<<<<<< HEAD
@Composable
fun MainSc(viewModel: ProtypeViewModel = ProtypeViewModel(), view: ProViewModel = ProViewModel(),onItemproclick: () -> Unit) {
    // Trạng thái chọn loại sản phẩm
    var selectedCategory by remember { mutableStateOf<Protype?>(null) }

    Column {
        ProtypeList(viewModel) { protypeItem ->
            selectedCategory = protypeItem
            // Gọi hàm lấy sản phẩm theo loại
            view.getProBycategoryy(protypeItem.name)
        }

        ProductList(view,onItemproclick)
    }
}

@Composable
fun ProtypeList(viewModel: ProtypeViewModel, onProtypeSelected: (Protype) -> Unit) {
    // Khởi chạy lấy danh sách các loại sản phẩm
    LaunchedEffect(Unit) {
        viewModel.fetchProtype()
    }

    // Hiển thị các loại sản phẩm trong LazyRow
    LazyRow(modifier = Modifier.fillMaxWidth().clickable {  }) {
        items(viewModel.protypeItems) { protypeItem ->
            ProtypeItem(protypeItem) {
                onProtypeSelected(protypeItem)
            }
        }
    }
}

@Composable
fun ProductList(view: ProViewModel,onClickItem:()->Unit) {
=======
//@Composable
//fun MainSc(navController: NavController,viewModel: ProtypeViewModel = ProtypeViewModel(), view: ProViewModel = ProViewModel()) {
//    // Trạng thái chọn loại sản phẩm
//    var selectedCategory by remember { mutableStateOf<Protype?>(null) }
//    LaunchedEffect(Unit) {
//        viewModel.fetchProtype()
//    }
//
//    Column {
//        ProtypeList() { protypeItem ->
//            selectedCategory = protypeItem
//            // Gọi hàm lấy sản phẩm theo loại
//            view.getProBycategoryy(protypeItem.name)
//        }
//
//        ProductList(navController,view)
//    }
//}

@Composable
fun ProtypeList(navController: NavController,viewModel: ProtypeViewModel=ProtypeViewModel(),view:ProViewModel=ProViewModel()) {
    // Khởi chạy lấy danh sách các loại sản phẩm
    val protypeItems by viewModel.protypeItems.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        if (protypeItems.isEmpty()) {
            viewModel.fetchProtype()
        }
    }
    var selectedCategory by remember { mutableStateOf<Protype?>(null) }
    // Hiển thị các loại sản phẩm trong LazyRow
    if (protypeItems.isEmpty()) {
        Text("No categories available", modifier = Modifier.padding(16.dp))
    } else {
        LazyRow {
            items(protypeItems) { protypeItem ->
                ProtypeItem(protypeItem) {
                    selectedCategory = protypeItem
                    view.getProBycategoryy(protypeItem.name)
                }
            }
        }
    }

    ProductList(navController,view)
}

@Composable
fun ProductList(navController: NavController,view: ProViewModel=ProViewModel()) {
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    // Theo dõi dữ liệu từ view.proo và hiển thị danh sách sản phẩm
    val proList by view.proo.observeAsState(initial = Response.success(emptyList()))

    LazyRow(
<<<<<<< HEAD
        modifier = Modifier.fillMaxWidth().clickable {  onClickItem}
    ) {
        items(proList.body() ?: emptyList()) { product ->
            ProductItem(product)
=======
        modifier = Modifier.fillMaxWidth()
    ) {
        items(proList.body() ?: emptyList()) { product ->
            ProductItem(product,navController)
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
        }
    }
}

@Composable
fun ProtypeItem(protypeItem: Protype, onClick: () -> Unit) {
    val context = LocalContext.current
    TextButton(
        onClick = {
            onClick()
            Toast.makeText(context, "Fetching products for ${protypeItem.name}", Toast.LENGTH_SHORT)
                .show()
        },
        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
    ) {
        Text(text = protypeItem.name ?: "Unknown", fontSize = 15.sp)

    }
}

@Composable
<<<<<<< HEAD
fun ProductItem(product: Product) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
=======
fun ProductItem(product: Product,navController: NavController) {
    val context = LocalContext.current
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    val screenWith = LocalConfiguration.current.screenWidthDp.dp

    Card(
        modifier = Modifier
            .fillMaxHeight(0.3f)  // Chiều cao bằng 4/10 chiều dài của màn hình
            .width(screenWith * 0.5f)
            .padding(start = 8.dp, end = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
<<<<<<< HEAD
            Toast.makeText(context, "Đây là ${product.name}", Toast.LENGTH_LONG).show()
=======
            navController.navigate("productDetail/${product.id}")

>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
        }
    ) {
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
fun AutoImage(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.mon1,
        R.drawable.mon2,
        R.drawable.mon3
    )
    val pager = rememberPagerState(pageCount = { images.size })
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pager.currentPage + 1) % pager.pageCount
            pager.scrollToPage(nextPage)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pager,
                modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) { currentPage ->
                Card(
                    modifier.wrapContentSize(),
                    elevation = CardDefaults.elevatedCardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "jiji",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth(0.7f)
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.25f),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            IconButton(
                onClick = {
                    val prePage = pager.currentPage - 1
                    if (prePage < images.size) {
                        scope.launch {
                            pager.scrollToPage(prePage)
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "",
                    modifier.fillMaxSize(), tint = Color.LightGray
                )
            }

            IconButton(
                onClick = {
                    val prePage = pager.currentPage - 1
                    if (prePage >= 0) {
                        scope.launch {
                            pager.scrollToPage(prePage)
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "",
                    modifier.fillMaxSize(), tint = Color.LightGray
                )
            }
        }
        PageIndicator(
            pageCount = images.size,
            currenPage = pager.currentPage,
            modifier = modifier
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currenPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelectted = it == currenPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelectted: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelectted) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(start = 2.dp, end = 2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelectted) Color(0xff373737) else Color(0xff373737))
    )

}

@Composable
fun Search() {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    SearchBar(query = text, onQueryChange = {
        text = it
    }, onSearch = {
        active = false
    }, active = active, onActiveChange = {
        active = it
    }, placeholder = {
        Text(text = "Search")
    }, leadingIcon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
    }, trailingIcon = {
        if (active) {
            Icon(modifier = Modifier.clickable {
                if (text.isNotEmpty()) {
                    text = ""
                } else {
                    active = false
                }
            }, imageVector = Icons.Default.Close, contentDescription = "close")
        }
    }) {
    }
}