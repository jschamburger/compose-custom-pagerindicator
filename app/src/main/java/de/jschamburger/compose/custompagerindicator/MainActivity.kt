package de.jschamburger.compose.custompagerindicator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import de.jschamburger.compose.custompagerindicator.ui.theme.MyApplicationTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Pager()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager() {
    val pagerState = rememberPagerState()

    val pages = listOf(
        Page("https://cdn.pixabay.com/photo/2014/02/19/20/39/winter-270160_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/11/23/03/08/valley-4646114_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2018/11/29/20/01/nature-3846403_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2016/11/19/14/38/camel-1839616_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2014/07/23/00/56/moon-399834_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/12/14/18/28/sunrise-4695484_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2018/03/29/07/35/water-3271579_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2021/01/23/13/01/hills-5942468_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/10/09/20/18/etretat-4538160_1280.jpg"),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = pages[page].url,
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Column {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Blue,
                    inactiveColor = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Red,
                    inactiveColor = Color.White,
                    indicatorShape = RoundedCornerShape(size = 2.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Yellow,
                    inactiveColor = Color.DarkGray,
                    indicatorShape = RectangleShape,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Black,
                    inactiveColor = Color(0xFF00BB00),
                    indicatorShape = CutCornerShape(size = 4.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                CustomPagerIndicatorFirstTry(
                    pagerState = pagerState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                CustomPagerIndicatorSecondTry(
                    pagerState = pagerState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                CustomPagerIndicatorSolution(
                    pagerState = pagerState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

private const val MULTIPLIER_SELECTED_PAGE = 4
private val baseWidth = 4.dp
private val spacing = 10.dp
private val height = 8.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomPagerIndicatorFirstTry(pagerState: PagerState, modifier: Modifier = Modifier, indicatorColor: Color = Color.Black) {
    Row {
        val currentPageWidth = baseWidth * (1 + (1 - abs(pagerState.currentPageOffset)) * MULTIPLIER_SELECTED_PAGE)
        val targetPageWidth = baseWidth * (1 + abs(pagerState.currentPageOffset) * MULTIPLIER_SELECTED_PAGE)

        Log.d(
            "Indicator First Try",
            "currentPage: ${pagerState.currentPage} targetPage ${pagerState.targetPage} offset ${pagerState.currentPageOffset} currentPageWidth: $currentPageWidth targetPageWidth: $targetPageWidth"
        )

        repeat(pagerState.pageCount) { index ->
            val width = when (index) {
                pagerState.currentPage -> currentPageWidth
                pagerState.targetPage -> targetPageWidth
                else -> baseWidth
            }
            Box(
                modifier = Modifier
                    .width(width)
                    .background(indicatorColor)
                    .height(height)
            )
            if (index != pagerState.pageCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomPagerIndicatorSecondTry(pagerState: PagerState, modifier: Modifier = Modifier, indicatorColor: Color = Color.Black) {
    Row {
        val currentPage = pagerState.currentPage
        val targetPage = if (pagerState.currentPageOffset < 0) currentPage - 1 else currentPage + 1
        val currentPageWidth = baseWidth * (1 + (1 - abs(pagerState.currentPageOffset)) * MULTIPLIER_SELECTED_PAGE)
        val targetPageWidth = baseWidth * (1 + abs(pagerState.currentPageOffset) * MULTIPLIER_SELECTED_PAGE)

        Log.d(
            "Indicator Second Try",
            "currentPage: $currentPage targetPage $targetPage offset ${pagerState.currentPageOffset} currentPageWidth: $currentPageWidth targetPageWidth: $targetPageWidth"
        )

        repeat(pagerState.pageCount) { index ->
            val width = when (index) {
                currentPage -> currentPageWidth
                targetPage -> targetPageWidth
                else -> baseWidth
            }
            Box(
                modifier = Modifier
                    .width(width)
                    .background(indicatorColor)
                    .height(height)
            )
            if (index != pagerState.pageCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomPagerIndicatorSolution(pagerState: PagerState, modifier: Modifier = Modifier, indicatorColor: Color = Color.Black) {
    Row {
        val offsetIntPart = pagerState.currentPageOffset.toInt()
        val offsetFractionalPart = pagerState.currentPageOffset - offsetIntPart
        val currentPage = pagerState.currentPage + offsetIntPart
        val targetPage = if (pagerState.currentPageOffset < 0) currentPage - 1 else currentPage + 1
        val currentPageWidth = baseWidth * (1 + (1 - abs(offsetFractionalPart)) * MULTIPLIER_SELECTED_PAGE)
        val targetPageWidth = baseWidth * (1 + abs(offsetFractionalPart) * MULTIPLIER_SELECTED_PAGE)

        Log.d(
            "Indicator Solution",
            "currentPage: $currentPage targetPage $targetPage offset ${pagerState.currentPageOffset} offsetIntPart $offsetIntPart offsetFractionalPart $offsetFractionalPart currentPageWidth: $currentPageWidth targetPageWidth: $targetPageWidth"
        )

        repeat(pagerState.pageCount) { index ->
            val width = when (index) {
                currentPage -> currentPageWidth
                targetPage -> targetPageWidth
                else -> baseWidth
            }
            Box(
                modifier = Modifier
                    .width(animateDpAsState(targetValue = width).value)
                    .width(width)
                    .background(indicatorColor)
                    .height(height)
            )
            if (index != pagerState.pageCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}

data class Page(val url: String)
