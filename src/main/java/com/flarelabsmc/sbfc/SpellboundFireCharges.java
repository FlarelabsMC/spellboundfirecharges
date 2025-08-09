package com.flarelabsmc.sbfc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SpellboundFireCharges.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellboundFireCharges {
    public static final String MODID = "sbfc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SpellboundFireCharges() {}
}
