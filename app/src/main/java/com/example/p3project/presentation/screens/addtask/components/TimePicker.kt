package com.example.p3project.presentation.screens.addtask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(visible: Boolean,
                     value: LocalTime,
                     onDismiss: (() -> Unit),
                     onConfirm: ((LocalTime) -> Unit)
) {
    var state = rememberTimePickerState(
        value.hour,
        value.minute,
        false
    )

    LaunchedEffect(value.hour, value.minute) {
        state = TimePickerState(value.hour, value.minute, false)
    }

    if (visible) {
        Dialog(onDismissRequest = onDismiss) {
            Card() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    TimePicker(
                        state = state,
                        layoutType = TimePickerLayoutType.Vertical,
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right
                    ) {
                        OutlinedButton(onClick = {
                            val time = LocalTime.of(state.hour, state.minute)
                            onConfirm(time)

                        }) {
                            Text(text = "Set")
                        }
                    }
                }
            }
        }
    }
}