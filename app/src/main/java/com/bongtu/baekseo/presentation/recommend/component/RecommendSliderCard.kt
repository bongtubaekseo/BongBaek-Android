package com.bongtu.baekseo.presentation.recommend.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_recommend_handshake
import com.bongtu.baekseo.R.drawable.ic_recommend_handshake_reversed
import com.bongtu.baekseo.R.drawable.ic_recommend_message
import com.bongtu.baekseo.R.drawable.ic_recommend_message_reversed
import com.bongtu.baekseo.R.string.recommendation_slider_frequently
import com.bongtu.baekseo.R.string.recommendation_slider_rarely
import com.bongtu.baekseo.R.string.recommendation_slider_title_contact
import com.bongtu.baekseo.R.string.recommendation_slider_title_meet
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendSliderCard(
    contactFrequency: Float,
    onContactFrequencyChange: (Float) -> Unit,
    meetFrequency: Float,
    onMeetFrequencyChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgDisplayCard,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(38.dp),
    ) {
        SliderCardItem(
            title = recommendation_slider_title_contact,
            iconRes = ic_recommend_message,
            thumbIconRes = ic_recommend_message_reversed,
            progress = contactFrequency,
            onProgressChange = onContactFrequencyChange,
        )

        SliderCardItem(
            title = recommendation_slider_title_meet,
            iconRes = ic_recommend_handshake,
            thumbIconRes = ic_recommend_handshake_reversed,
            progress = meetFrequency,
            onProgressChange = onMeetFrequencyChange,
        )
    }
}

@Composable
fun SliderCardItem(
    @StringRes title: Int,
    @DrawableRes iconRes: Int,
    @DrawableRes thumbIconRes: Int,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(title),
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BongBaekSlider(
            iconRes = thumbIconRes,
            progress = progress,
            onProgressChange = onProgressChange,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(recommendation_slider_rarely),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )

            Text(
                text = stringResource(recommendation_slider_frequently),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BongBaekSlider(
    @DrawableRes iconRes: Int,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var thumbSize by remember { mutableStateOf(IntSize.Zero) }

    BoxWithConstraints(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val trackWidth = size.width - thumbSize.width
                    val currentX = (change.position.x - thumbSize.width / 2f)
                        .coerceIn(0f, trackWidth.toFloat())
                    val newProgress = (currentX / trackWidth).coerceIn(0f, 1f)
                    onProgressChange(newProgress)
                }
            },
    ) {
        val trackWidth = this.constraints.maxWidth.toFloat() - thumbSize.width
        val thumbOffset = trackWidth * progress

        SliderTrack(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        )

        SliderThumb(
            iconRes = iconRes,
            modifier = Modifier
                .offset { IntOffset(thumbOffset.toInt(), 0) }
                .onGloballyPositioned { thumbSize = it.size }
                .align(Alignment.CenterStart),
        )
    }
}

@Composable
private fun SliderThumb(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgStatusFocused,
                shape = CircleShape,
            )
            .padding(8.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = BongBaekTheme.colors.bgStatusFocused,
        )
    }
}

@Composable
private fun SliderTrack(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(BongBaekTheme.colors.bgDisplayRange),
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(progress)
                .background(BongBaekTheme.colors.bgStatusFocused),
        )
    }
}

@Preview
@Composable
private fun RecommendSliderCardPreview() {
    BongBaekTheme {
        var progress1 by remember { mutableStateOf(0f) }
        var progress2 by remember { mutableStateOf(0f) }

        Column {
            RecommendSliderCard(
                contactFrequency = progress1,
                onContactFrequencyChange = { progress1 = it },
                meetFrequency = progress2,
                onMeetFrequencyChange = { progress2 = it },
                modifier = Modifier
            )
        }
    }
}
