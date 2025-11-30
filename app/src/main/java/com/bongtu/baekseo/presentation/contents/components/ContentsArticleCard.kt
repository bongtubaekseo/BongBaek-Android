package com.bongtu.baekseo.presentation.contents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

private const val CARD_RATIO = 320 / 240f

@Composable
fun ContentsArticleCard(
    imageUrl: String,
    onCardClick: () -> Unit,
    eventType: String,
    title: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BongBaekTheme.colors.cardStart,
            BongBaekTheme.colors.cardEnd,
        ),
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundGradient)
            .noRippleClickable(onCardClick),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(CARD_RATIO),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // TODO: 금액추천 카드 디자인 픽스되면 컴포넌트로 뺄지 생각 필요
            Text(
                text = eventType,
                modifier = Modifier
                    .background(
                        color = BongBaekTheme.colors.bgDisplayCard,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp,
                    ),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )

            Text(
                text = title,
                color = BongBaekTheme.colors.txtInteractiveInverse,
                style = BongBaekTheme.typography.titleSemiBold18,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = date,
                color = BongBaekTheme.colors.txtDisplayTertiary,
                style = BongBaekTheme.typography.captionRegular12,
            )
        }
    }
}

@Preview
@Composable
private fun ContentsArticleCardPreview() {
    BongBaekTheme {
        ContentsArticleCard(
            imageUrl = "",
            onCardClick = { },
            eventType = "결혼식",
            title = "제목이 오는 위치제목이 오는 위치제목이 오는 위치제목이 오는 위치",
            date = "날짜가 오는 위치"
        )
    }
}
