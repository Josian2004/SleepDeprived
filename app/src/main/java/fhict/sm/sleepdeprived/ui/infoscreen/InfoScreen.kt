package fhict.sm.sleepdeprived

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily


class TipObject(val title: String, val paragraph: String, val imageId: Int) {
}



@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        InfoHeader()
        TipCharacter()
        TipList()
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
fun TipCharacter() {
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
                text = "I’ve noticed that you wake up a lot during the night, this might be because you have a noisy bedroom.",
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
fun TipList() {
    val paragraph = "Lorem ipsum dolor sit amet consectetur adipiscing elit Ut et massa mi. Aliquam in hendrerit urna. Pellentesque sit amet sapien fringilla, mattis ligula consectetur, ultrices mauris. Maecenas vitae mattis tellus. Nullam quis imperdiet augue. Vestibulum auctor ornare leo, non suscipit magna interdum eu. Curabitur pellentesque nibh nibh, at maximus ante fermentum sit amet. Pellentesque commodo lacus at sodales sodales."
    val tips = arrayListOf<TipObject>(
        TipObject("Tips on how to reduce noise in your bedroom during the night", paragraph, R.drawable.noise_bedroom_stock),
        TipObject("Drink less caffeinated drinks before you go to bed", paragraph, R.drawable.coffee_stock),
        TipObject("Reduce the temperature of your bedroom to increase the quality of your sleep", paragraph, R.drawable.temperature_stock)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        tips.forEach {
                tipObject ->
                Tip(
                    title = tipObject.title,
                    paragraph = tipObject.paragraph,
                    imageId = tipObject.imageId
                )
        }
    }
}

@Composable
fun Tip(title: String, paragraph: String, imageId: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(9.dp))
            .width(300.dp)
            .background(MaterialTheme.colors.primary)
            .clickable {  },
        contentAlignment = Alignment.Center

    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Tip Picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
            }
    }
    Spacer(modifier = Modifier.height(30.dp))
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