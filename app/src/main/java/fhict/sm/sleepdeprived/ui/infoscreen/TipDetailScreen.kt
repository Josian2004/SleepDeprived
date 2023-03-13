package fhict.sm.sleepdeprived.ui.infoscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TipDetailScreen(title: String?, paragraphs: ArrayList<String>?, image: Int?, closePopup: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)

    )
    {
        Spacer(modifier = Modifier
            .weight(0.02f)
            .fillMaxWidth())
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .weight(0.96f)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary),
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth(),
            ) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .offset(x = (-4).dp, y = 0.dp)
                        .size(30.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { closePopup() }
                )
            }
            image?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "Tip Picture",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            if (title != null) {
                Text(
                    text = title,
                    fontSize = 21.sp,
                    modifier = Modifier.padding(7.dp)
                )
            }

            paragraphs?.forEach { paragraph ->
                Text(
                    text = paragraph,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(7.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier
            .weight(0.02f)
            .fillMaxWidth())
    }
    Spacer(modifier = Modifier.height(20.dp))


    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        image?.let { painterResource(id = it) }
            ?.let {
                Image(
                    painter = it,
                    contentDescription = "Tip Image",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        if (title != null) {
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
            )
        }
        paragraphs?.forEach { paragraph ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = paragraph)
        }
    }*/
}