package ai.rever.boss.plugin.dynamic.nanorx

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.PluginContext
import ai.rever.boss.plugin.browser.BrowserService

/**
 * NanoRx dynamic plugin.
 *
 * Embeds the NanoRx precision-oncology dashboard in a sidebar panel using the
 * host's BrowserService.
 */
class NanoRxPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.nanorx"
    override val displayName: String = "NanoRx"
    override val version: String = "1.0.0"
    override val description: String = "Precision oncology + DNA-nanobot delivery dashboard in a side panel"
    override val author: String = "Risa Labs Inc."
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-nanorx"

    private var browserService: BrowserService? = null

    override fun register(context: PluginContext) {
        // Providers may be null — store it and let the UI handle the null case.
        browserService = context.browserService

        context.panelRegistry.registerPanel(NanoRxInfo) { ctx, panelInfo ->
            NanoRxComponent(ctx, panelInfo, browserService)
        }
    }
}
