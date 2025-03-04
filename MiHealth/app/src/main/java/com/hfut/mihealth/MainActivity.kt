package com.hfut.mihealth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val navItems = listOf(
        BottomItemDate("HomePage", "主页", R.drawable.homepage_icon),
        BottomItemDate("recode", "记录", R.drawable.recode_icon),
        BottomItemDate("camera", "拍照", R.drawable.mine_icon),
        BottomItemDate("mine", "我的", R.drawable.mine_icon)
    )
    val navPos = rememberNavController()
    val backgroundPainter: Painter = painterResource(R.drawable.background)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "首页") },
                modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars),
            )
        },
        bottomBar = {
            BottomNavigationBar(navItems, navPos)
        }
    ) { innerPadding ->
        print(innerPadding)
        Box(modifier = Modifier.padding(innerPadding)) {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            NavHost(navController = navPos, startDestination = "HomePage") {
                composable("HomePage") {
                    HomePageScreen(navPos)
                }
                composable("recode") {
                    AppContent("记录")
                }
                composable("mine") {
                    MinePageScreen()
                }
                composable("camera") {
                    CameraPageScreen()
                }
            }
        }
    }

}

@Composable
fun AppContent(item: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(item)
    }
}

@Composable
fun MinePageScreen() {
    Image(
        painter = painterResource(R.drawable.mine),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp
            ),
    )
}

@Composable
fun BottomNavigationBar(items: List<BottomItemDate>, navPos: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        items.forEachIndexed { index, s ->
            NavigationBarItem(
                icon = { Icon(ImageVector.vectorResource(s.icon), contentDescription = null) },
                label = { Text(s.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navPos.navigate(s.route) {
                        //使用此方法,可以避免生成一个重复的路由堆栈
                        popUpTo(navPos.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //避免重复选择会创建一个新的页面副本
                        launchSingleTop = true
                        //当重新选择之前已选择项目恢复页面状态
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomItemDate(val route: String, val label: String, val icon: Int)


