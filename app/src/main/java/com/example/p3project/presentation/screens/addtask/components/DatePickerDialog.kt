package com.example.p3project.presentation.screens.addtask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(visible: Boolean,
                     value: LocalDate,
                     onDismiss: (() -> Unit),
                     onConfirm: ((LocalDate) -> Unit)
) {
    var state = rememberDatePickerState(value.toEpochDay() * 86400 * 1000
    )

    LaunchedEffect(value.toEpochDay()) {
        state = DatePickerState(Locale.getDefault(),
            value.toEpochDay() * 86400 * 1000
        )
    }


    if (visible) {
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = {
            onDismiss()
        }) {
            Card(modifier = Modifier.width(375.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    DatePicker(
                        state = state,
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right
                    ) {
                        OutlinedButton(onClick = {
                            // Hacky - The m3 DatePicker is experimental wants to work in ms,
                            // so we have to convert to a localDateTime, only to convert right back
                            val now = OffsetDateTime.now()
                            val pickedDate = LocalDateTime.ofEpochSecond(
                                                state.selectedDateMillis?.div(1000)!!,
                                    0, now.offset
                                             ).toLocalDate()

                            onConfirm(pickedDate) }) {
                            Text(text = "Set")
                        }
                    }
                }
            }
        }
    }
}