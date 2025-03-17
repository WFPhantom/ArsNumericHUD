package com.wfphantom.arsnumerichud;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ScreenEvent;

@Mod(ArsNumericHUD.MODID)
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber
public class ArsNumericHUD
{
    public static final String MODID = "arsnumerichud";
     /// Credits to Moonwolf287 for original implementation
     /// [...](https://github.com/Moonwolf287/ArsEnderStorage/blob/1.16.5/src/main/java/io/github/moonwolf287/ars_enderstorage/ManaTextGUI.java)
     /// Original implementation in 1.16.5
     @SubscribeEvent
     public static void drawTopGui(ScreenEvent.Opening event){
         ArsNouveauAPI.ENABLE_DEBUG_NUMBERS = true;
     }
}