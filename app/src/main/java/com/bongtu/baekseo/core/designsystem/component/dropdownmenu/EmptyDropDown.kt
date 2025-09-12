package com.bongtu.baekseo.core.designsystem.component.dropdownmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_info_primary
import com.bongtu.baekseo.R.string.search_result_empty
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun EmptyDropDown(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(vertical = 49.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_info_primary),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(search_result_empty),
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.body1Medium16,
        )
    }
}

@Preview
@Composable
private fun EmptyDropDownPreview() {
    BongBaekTheme {
        EmptyDropDown()
    }
}
