package com.hfut.mihealth.commen.mainPage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hfut.mihealth.R
import com.hfut.mihealth.commen.foodRecord.ui.CameraPageScreen
import com.hfut.mihealth.commen.mainPage.ui.HomePageScreen
import com.hfut.mihealth.commen.mainPage.viewmodel.mainViewModelclass
import com.hfut.mihealth.network.client.AuthInterceptor
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ThemeWhite
import com.hfut.mihealth.util.SharedPreferencesHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

private suspend fun initToken(context: Context, viewModel: mainViewModelclass) {
    val sharedPreferences = SharedPreferencesHelper(context)
    val savedToken = sharedPreferences.getToken()
    if (!savedToken.isNullOrBlank()) {
        // 如果有token，直接进入主界面
        AuthInterceptor.setToken(savedToken)
    } else {
        viewModel.guestLogin()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(viewModel: mainViewModelclass = viewModel()) {
    // 在这里调用 initToken 方法
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        initToken(context, viewModel)
    }
    val navItems = listOf(
        BottomItemDate("HomePage", "主页", R.drawable.homepage_icon),
        BottomItemDate("record", "记录", R.drawable.record_icon),
//        BottomItemDate("camera", "拍照", R.drawable.mine_icon),
        BottomItemDate("mine", "我的", R.drawable.mine_icon)
    )
    val navPos = rememberNavController()
    val backgroundPainter: Painter = painterResource(R.drawable.background)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        },
        bottomBar = {
            BottomNavigationBar(navItems, navPos)
        }
    ) { innerPadding ->
        print(innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            NavHost(
                navController = navPos, startDestination = "HomePage",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("HomePage") {
                    HomePageScreen(navPos)
                }
                composable("record") {
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
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
fun BottomNavigationBar(items: List<BottomItemDate>, navPos: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(
        modifier = Modifier.fillMaxWidth(1f),
        containerColor = ThemeWhite
    ) {
        items.forEachIndexed { index, s ->
            NavigationBarItem(
                icon = { Icon(ImageVector.vectorResource(s.icon), contentDescription = null) },
                label = { Text(s.label) },
                selected = selectedItem == index,
                colors = NavigationBarItemColors(
                    selectedIconColor = Green,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
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


