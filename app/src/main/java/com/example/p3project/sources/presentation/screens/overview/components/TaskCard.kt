package com.example.p3project.sources.presentation.screens.overview.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.p3project.sources.data.Task

@Composable
fun TaskCard (task: Task, onClick: () -> Unit) {
    ElevatedCard (modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 100.dp)
        .padding(2.dp),
        onClick = onClick
          )

    {
        Column (modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ){
            Box (modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.name, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.id.toString(), overflow = TextOverflow.Ellipsis,
                    color = Color.Magenta,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Right
                )
            }

            Text(text = task.description,
                 style = MaterialTheme.typography.bodyMedium)

            FilledTonalButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().align(Alignment.End),

            ) {
                Text(text = "Complete")
            }

        }
    }
}