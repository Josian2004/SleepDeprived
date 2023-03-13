package fhict.sm.sleepdeprived

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fhict.sm.sleepdeprived.ui.infoscreen.InfoUiState
import fhict.sm.sleepdeprived.ui.infoscreen.InfoViewModel
import fhict.sm.sleepdeprived.ui.infoscreen.TipDetailScreen
import fhict.sm.sleepdeprived.ui.infoscreen.TipModel
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily





@Composable
fun InfoScreen(
    infoViewModel: InfoViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = ScrollState(0))
    ) {
        val infoUiState by infoViewModel.uiState.collectAsState()

        if (!infoUiState.showPopUp) {
            InfoHeader()
            TipCharacter(infoUiState)
            TipList(infoUiState, infoViewModel::openTipDetailPopup, infoViewModel::openTooMuchCaffeinePopup)
        } else {
            TipDetailScreen(title = infoUiState.selectedTip?.tipTitle, paragraphs = infoUiState.selectedTip?.getTipParagraphs(), image = infoUiState.selectedTip?.tipImage, infoViewModel::closeTipDetailPopup)
        }
        //Spacer(modifier = Modifier.height(20.dp)
    }
}



@Composable
fun InfoHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = "Tips for you",
            fontFamily = aBeeZeeFamily,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 25.dp, start = 20.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = 20.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Gray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture"
            )
        }
    }
}

@Composable
fun TipCharacter(infoUiState: InfoUiState) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Box(modifier = Modifier
            .offset(x = 0.dp, y = 0.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 30.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(MaterialTheme.colors.primary)
            .height(150.dp)
            .width(200.dp)
        ) {
            Text(
                text = infoUiState.characterText,
                fontSize = 17.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp)
            )
        }
        Box(modifier = Modifier
            .offset(x = 92.dp, y = 75.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = TriangleEdgeShape(80)
            )
            .width(8.dp)
            .fillMaxHeight()
        ) {

        }
        Image(
            painter = painterResource(id = R.drawable.muts),
            contentDescription = "Character",
            modifier = Modifier
                .width(230.dp)
                .offset(x = 165.dp, y = 20.dp)
        )
    }
}

@Composable
fun TipList(
    infoUiState: InfoUiState,
    openPopup: (selectedTip: TipModel) -> Unit,
    openTooMuchCaffeinePopup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (infoUiState.tooMuchCaffeine or infoUiState.recommendedTips.isNotEmpty()) {
            Text(
                text = "Recommended Tips for You",
                modifier = Modifier.padding(top = 20.dp)
            )
            Divider(
                color = MaterialTheme.colors.onPrimary,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            )
        }


        if (infoUiState.tooMuchCaffeine) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .width(300.dp)
                    .background(MaterialTheme.colors.primary)
                    .clickable { openTooMuchCaffeinePopup() },
                contentAlignment = Alignment.Center

            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.caffeine_warning_stock),
                        contentDescription = "Tip Picture",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Warning! You drank far too much caffeine!",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }



        infoUiState.recommendedTips.forEach {
                tipObject ->
            Tip(tip = tipObject, openPopup)
        }

        Text(
            text = "Extra General Tips",
            modifier = Modifier.padding(top = 20.dp)
        )
        Divider(
            color = MaterialTheme.colors.onPrimary,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
        infoUiState.otherTips.forEach {
                tipObject ->
            Tip(tip = tipObject, openPopup)
        }
    }
}

@Composable
fun Tip(tip: TipModel, openPopup:(selectedTip: TipModel) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(9.dp))
            .width(300.dp)
            .background(MaterialTheme.colors.primary)
            .clickable { openPopup(tip) },
        contentAlignment = Alignment.Center

    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = tip.tipImage),
                contentDescription = "Tip Picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = tip.tipTitle,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
            }
    }
    Spacer(modifier = Modifier.height(20.dp))
}


class TriangleEdgeShape(private val offset: Int) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height-offset)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f + offset, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}

//.border(width = 1.dp, Color.Red)