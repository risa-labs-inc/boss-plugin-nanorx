package ai.rever.boss.plugin.dynamic.nanorx

import ai.rever.boss.plugin.api.Panel.Companion.right
import ai.rever.boss.plugin.api.Panel.Companion.top
import ai.rever.boss.plugin.api.PanelId
import ai.rever.boss.plugin.api.PanelInfo
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech

object NanoRxInfo : PanelInfo {
    override val id = PanelId("nanorx", 18)
    override val displayName = "NanoRx"
    override val icon = Icons.Outlined.Biotech
    override val defaultSlotPosition = right.top.top
}
