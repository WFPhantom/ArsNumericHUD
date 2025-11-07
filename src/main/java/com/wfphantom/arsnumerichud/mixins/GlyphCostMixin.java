package com.wfphantom.arsnumerichud.mixins;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.data.ReactiveCasterData;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class GlyphCostMixin {
    @Redirect(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"))
    public void tooltip(Item instance, ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
        instance.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (ArsNouveau.proxy.getMinecraft() == null) return;
        Player player = ArsNouveau.proxy.getPlayer();
        int cost;
        if (instance instanceof Glyph glyph) cost = glyph.spellPart.getCastingCost();
        else if (instance instanceof ICasterTool casterTool && casterTool.getSpellCaster(stack) instanceof SpellCaster casterData) {
            Spell spell = casterData.getSpell(casterData.getCurrentSlot());
            if (spell.isEmpty()) return;
            cost = spell.getCost() - ManaUtil.getPlayerDiscounts(player, spell, stack);
        } else if (stack.get(DataComponentRegistry.REACTIVE_CASTER) instanceof ReactiveCasterData reactiveCasterData) {
            Spell casterData = reactiveCasterData.getSpell();
            if (casterData.isEmpty()) return;
            cost = casterData.getCost() - ManaUtil.getPlayerDiscounts(player, casterData, stack);
        } else return;
        tooltipComponents.add(Component.translatable("Mana Cost: ", cost).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)).append(String.valueOf(cost)));
    }
}