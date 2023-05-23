package com.github.abuballan.coilchucker.internal.ui.requests

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import com.github.abuballan.coilchucker.internal.ui.theme.CoilChuckerTheme

@Composable
internal fun TransactionItem(
    transaction: HttpTransaction
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (datasourceRef, pathRef, rowRef, errorMsgRef, dividerRef) = createRefs()
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

        val bottomBarrier = createBottomBarrier(errorMsgRef, rowRef)

        val datasource = when (transaction.status) {
            HttpTransaction.Status.Requested -> {
                "[Requested]"
            }

            HttpTransaction.Status.Complete -> "[${transaction.datasource ?: ""}]"
            HttpTransaction.Status.Failed -> "[Error]"
        }

        Text(
            modifier = Modifier.constrainAs(datasourceRef) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(startGuideline)
            },
            text = datasource
        )
        Text(
            modifier = Modifier.constrainAs(pathRef) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(datasourceRef.end, 8.dp)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
            },
            text = transaction.path ?: ""
        )

        if (transaction.status == HttpTransaction.Status.Failed)
            Text(
                modifier = Modifier
                    .constrainAs(rowRef) {
                        top.linkTo(pathRef.bottom, 8.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    },
                text = transaction.error ?: ""
            )

        if (transaction.status == HttpTransaction.Status.Complete)
            Row(modifier = Modifier
                .constrainAs(rowRef) {
                    top.linkTo(pathRef.bottom, 8.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${transaction.date}"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${transaction.tookMs.toString()} ms"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${transaction.width} x ${transaction.height}"
                )
            }

        Divider(
            modifier = Modifier.constrainAs(dividerRef) {
                top.linkTo(bottomBarrier, 8.dp)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    val httpTransaction = HttpTransaction(
        path = "path",
        datasource = "DISK",
        tookMs = 0,
        error = null,
        width = 0L,
        height = 0L,
        date = ""
    )
    CoilChuckerTheme {
        TransactionItem(
            transaction = httpTransaction
        )
    }
}