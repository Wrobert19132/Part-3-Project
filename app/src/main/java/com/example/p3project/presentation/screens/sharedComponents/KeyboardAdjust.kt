package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.launch

@Composable
fun KeyboardAdjust(content: (@Composable () -> Unit)) {
    val scrollState: ScrollState = rememberScrollState()
    val keyboardHeight = WindowInsets.ime.getTop(LocalDensity.current)

    val scope = rememberCoroutineScope()

    LaunchedEffect(keyboardHeight) {
        scope.launch {scrollState.scrollBy(keyboardHeight.toFloat())}
    }

    Box(
        modifier=Modifier.imePadding()
    ) {
        content()
    }
}