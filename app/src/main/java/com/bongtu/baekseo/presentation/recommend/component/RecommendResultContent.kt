package com.bongtu.baekseo.presentation.recommend.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check
import com.bongtu.baekseo.R.drawable.ic_event
import com.bongtu.baekseo.R.drawable.ic_info_primary
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.string.recommendation_result_amount_card_title
import com.bongtu.baekseo.R.string.recommendation_result_amount_card_unit
import com.bongtu.baekseo.R.string.recommendation_result_confirm
import com.bongtu.baekseo.R.string.recommendation_result_edit
import com.bongtu.baekseo.R.string.recommendation_result_event_type
import com.bongtu.baekseo.R.string.recommendation_result_inform_desc
import com.bongtu.baekseo.R.string.recommendation_result_inform_title
import com.bongtu.baekseo.R.string.recommendation_result_location
import com.bongtu.baekseo.R.string.recommendation_result_logic_desc
import com.bongtu.baekseo.R.string.recommendation_result_logic_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.progressbar.BongBaekProgressBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import java.text.DecimalFormat

private const val MIN_AMOUNT = 50_000
private const val MAX_AMOUNT = 200_000

@Composable
fun RecommendResultContent(
    expense: Int,
    minExpense: Int,
    maxExpense: Int,
    isLottieEnded: Boolean,
    onConfirmClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddedModifier = Modifier.padding(horizontal = 20.dp)

    Column(
        modifier = modifier
            .background(BongBaekTheme.colors.gray900),
    ) {
        RecommendAmountCard(
            expense = expense,
            minExpense = minExpense,
            maxExpense = maxExpense,
            isLottieEnded = isLottieEnded,
            modifier = paddedModifier,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = paddedModifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            RecommendEventInfoCard(
                iconRes = ic_event,
                titleRes = recommendation_result_event_type,
                value = "결혼식", // TODO: 실제 value로 대체
                modifier = Modifier.weight(1f),
            )

            RecommendEventInfoCard(
                iconRes = ic_location,
                titleRes = recommendation_result_location,
                value = "강남 웨딩홀웨딩홀웨딩홀웨딩홀",
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        AnimatedVisibility(
            visible = isLottieEnded,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = BongBaekTheme.colors.black,
                )

                Spacer(modifier = Modifier.height(36.dp))

                ResultDescriptionCard(
                    iconRes = ic_info_primary,
                    titleRes = recommendation_result_logic_title,
                    descriptionRes = recommendation_result_logic_desc,
                    modifier = paddedModifier,
                )

                Spacer(modifier = Modifier.height(14.dp))

                ResultDescriptionCard(
                    iconRes = ic_check,
                    titleRes = recommendation_result_inform_title,
                    descriptionRes = recommendation_result_inform_desc,
                    modifier = paddedModifier,
                )

                Spacer(modifier = Modifier.height(40.dp))

                BongBaekButton(
                    title = stringResource(recommendation_result_confirm),
                    onClick = onConfirmClick,
                    buttonType = ButtonType.PRIMARY,
                    modifier = paddedModifier
                        .fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(10.dp))

                BongBaekButton(
                    title = stringResource(recommendation_result_edit),
                    onClick = onEditClick,
                    buttonType = ButtonType.SECONDARY,
                    modifier = paddedModifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 46.dp,
                        ),
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
private fun RecommendAmountCard(
    expense: Int,
    minExpense: Int,
    maxExpense: Int,
    isLottieEnded: Boolean,
    modifier: Modifier = Modifier,
) {
    val target = remember { expense / maxExpense.toFloat() }
    var animatedProgress by remember { mutableFloatStateOf(0f) }
    val decimalFormatter = remember { DecimalFormat("#,###") }
    val bongBaekColors = BongBaekTheme.colors
    val progressGradient = remember {
        Brush.horizontalGradient(
            listOf(
                bongBaekColors.primaryStrong,
                bongBaekColors.primaryNormal,
            )
        )
    }

    LaunchedEffect(isLottieEnded) {
        if (isLottieEnded) {
            animate(
                initialValue = 0f,
                targetValue = target,
                animationSpec = tween(durationMillis = 1000),
            ) { value, _ ->
                animatedProgress = value
            }
        }
    }

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.black,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
    ) {
        Text(
            text = stringResource(recommendation_result_amount_card_title),
            style = BongBaekTheme.typography.titleSemiBold18,
            color = BongBaekTheme.colors.white,
        )

        Spacer(modifier = Modifier.height(12.dp))

        BongBaekProgressBar(
            progress = animatedProgress,
            modifier = Modifier,
            backgroundColor = BongBaekTheme.colors.gray700,
            progressColor = progressGradient,
            height = 12.dp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(
                    recommendation_result_amount_card_unit,
                    decimalFormatter.format(minExpense)
                ),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.gray400,
            )

            Text(
                text = stringResource(
                    recommendation_result_amount_card_unit,
                    decimalFormatter.format(maxExpense)
                ),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Composable
private fun RecommendEventInfoCard(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.gray900,
                    shape = CircleShape,
                )
                .padding(10.dp)
                .size(22.dp),
            tint = BongBaekTheme.colors.primaryNormal,
        )

        Column {
            Text(
                text = stringResource(titleRes),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.gray400,
            )

            Text(
                text = value,
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.white,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun ResultDescriptionCard(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(titleRes),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
            )
        }

        Text(
            text = stringResource(descriptionRes),
            style = BongBaekTheme.typography.body2Regular16,
            color = BongBaekTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun RecommendResultContentPreview() {
    BongBaekTheme {
        var isLottieEnded by remember { mutableStateOf(true) }

        RecommendResultContent(
            expense = 125_000,
            minExpense = 50_000,
            maxExpense = 125_000,
            isLottieEnded = isLottieEnded,
            onConfirmClick = {},
            onEditClick = {},
            modifier = Modifier.noRippleClickable { isLottieEnded = !isLottieEnded },
        )
    }
}
