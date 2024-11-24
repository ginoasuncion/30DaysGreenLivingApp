package tat.mukhutdinov.lesson9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import tat.mukhutdinov.lesson9.data.GreenLivingTask
import tat.mukhutdinov.lesson9.data.greenLivingTasks
import tat.mukhutdinov.lesson9.ui.theme.GreenLivingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenLivingTheme {
                GreenLivingApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenLivingTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    )
}


@Composable
fun GreenLivingApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GreenLivingTopAppBar()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(greenLivingTasks) { task ->
                GreenLivingItem(task = task)
            }
        }
    }
}

@Composable
fun GreenLivingItem(
    task: GreenLivingTask,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.medium,
        elevation = cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Day ${task.day}",
                    style = MaterialTheme.typography.displayMedium, // Make Day text larger
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        painter = painterResource(
                            id = if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more
                        ),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp)) // Reduce spacing between Day and Title

            Text(
                text = stringResource(task.titleResId),
                style = MaterialTheme.typography.displaySmall
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = task.imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(task.descriptionResId),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun GreenLivingAppPreview() {
    GreenLivingTheme {
        GreenLivingApp()
    }
}

@Preview
@Composable
fun GreenLivingItemPreview() {
    GreenLivingTheme {
        GreenLivingItem(
            task = GreenLivingTask(
                day = 1,
                titleResId = R.string.task_1_title,
                descriptionResId = R.string.task_1_desc,
                imageResId = R.drawable.img1
            )
        )
    }
}
