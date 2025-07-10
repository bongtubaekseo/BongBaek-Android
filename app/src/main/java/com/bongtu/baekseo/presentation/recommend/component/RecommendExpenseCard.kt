package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bongtu.baekseo.R.string.kr_won
import com.bongtu.baekseo.R.string.recommendation_envelope_description
import com.bongtu.baekseo.R.string.recommendation_envelope_title
import com.bongtu.baekseo.R.string.recommendation_result_topbar
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.designsystem.component.chip.BongBaekLabelChip
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import java.text.DecimalFormat

@Composable
fun RecommendExpenseCard(
    event: EventType,
    expense: Int,
    isLottieEnded: Boolean,
    modifier: Modifier = Modifier,
) {
    val decimalFormatter = remember { DecimalFormat("#,###") }
    val bongBaekColors = BongBaekTheme.colors
    val gradientBackground = remember {
        Brush.linearGradient(
            colors = listOf(
                bongBaekColors.gradientEnvelopeStart,
                bongBaekColors.gradientEnvelopeEnd,
            ),
            start = Offset(0f, 0f),
            end = Offset.Infinite,
        )
    }
    val gradientText = remember {
        Brush.linearGradient(
            colors = listOf(
                bongBaekColors.gradientExpenseStart,
                bongBaekColors.gradientExpenseEnd,
            ),
        )
    }
    val descriptionColor = remember { bongBaekColors.gray750.copy(alpha = 0.6f) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Crossfade(
            targetState = isLottieEnded,
        ) { ended ->
            if (ended) {
                BongBaekLabelChip(
                    event = event,
                )
            }
        }

        AnimatedVisibility(
            visible = isLottieEnded,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            ) + fadeIn(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = gradientBackground,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(recommendation_result_topbar),
                    modifier = Modifier
                        .background(
                            color = bongBaekColors.primaryNormal,
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 2.dp,
                        ),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.white,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = decimalFormatter.format(expense),
                        style = BongBaekTheme.typography.headBold24.copy(
                            fontSize = 44.sp,
                            fontWeight = FontWeight.ExtraBold,
                            brush = gradientText,
                            letterSpacing = (-0.04).em,
                        )
                    )

                    Text(
                        text = stringResource(kr_won),
                        style = BongBaekTheme.typography.titleSemiBold20,
                        color = BongBaekTheme.colors.gray600,
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = descriptionColor,
                            shape = RoundedCornerShape(10.dp),
                        )
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = stringResource(recommendation_envelope_title),
                        style = BongBaekTheme.typography.body1Medium16,
                        color = BongBaekTheme.colors.white,
                    )

                    Text(
                        text = stringResource(recommendation_envelope_description),
                        style = BongBaekTheme.typography.body2Regular14,
                        color = BongBaekTheme.colors.gray200,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecommendExpenseCardPreview() {
    BongBaekTheme {
        var isLottieEnded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable { isLottieEnded = !isLottieEnded },
        ) {
            RecommendExpenseCard(
                event = EventType.BIRTHDAY,
                expense = 100_000,
                isLottieEnded = isLottieEnded,
                modifier = Modifier
            )
        }
    }
}
