package ai.rever.boss.plugin.dynamic.nanorx

import ai.rever.boss.plugin.api.PanelComponentWithUI
import ai.rever.boss.plugin.api.PanelInfo
import ai.rever.boss.plugin.browser.BrowserService
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

class NanoRxComponent(
    ctx: ComponentContext,
    override val panelInfo: PanelInfo,
    private val browserService: BrowserService?,
) : PanelComponentWithUI, ComponentContext by ctx {

    @Composable
    override fun Content() {
        NanoRxContent(browserService)
    }
}
