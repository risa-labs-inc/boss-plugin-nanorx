package ai.rever.boss.plugin.dynamic.nanorx

import ai.rever.boss.plugin.browser.BrowserConfig
import ai.rever.boss.plugin.browser.BrowserHandle
import ai.rever.boss.plugin.browser.BrowserService
import ai.rever.boss.plugin.ui.BossTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val NANORX_URL = "https://nanorx-discovery.web.app"

@Composable
fun NanoRxContent(browserService: BrowserService?) {
    BossTheme {
        // Graceful fallback when the embedded browser isn't available.
        if (browserService == null || !browserService.isAvailable()) {
            MessageBox("Browser service not available")
            return@BossTheme
        }

        var handle by remember { mutableStateOf<BrowserHandle?>(null) }

        // Create the browser once, when the panel first appears.
        LaunchedEffect(Unit) {
            handle = browserService.createBrowser(BrowserConfig(url = NANORX_URL))
        }

        // Always release the browser when the panel is closed.
        DisposableEffect(Unit) {
            onDispose { handle?.dispose() }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            val current = handle
            when {
                current == null -> Loading()
                current.isValid -> current.Content()   // renders the live web app
                else -> MessageBox("Failed to load NanoRx")
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MessageBox(text: String) {
    Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text(text, color = MaterialTheme.colors.onBackground)
    }
}
