package com.alex.mysticalagriculture.items.tool;

import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.augment.MiningAOEAugment;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseShovelItem;
import draylar.magna.api.BlockProcessor;
import draylar.magna.api.MagnaTool;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class EssenceShovelItem extends BaseShovelItem implements ITinkerable, MagnaTool {
    private static final EnumSet<AugmentType> TYPES = EnumSet.of(AugmentType.TOOL, AugmentType.SHOVEL);
    private final int tinkerableTier;
    private final int slots;

    public EssenceShovelItem(ToolMaterial tier, int tinkerableTier, int slots) {
        super(tier);
        this.tinkerableTier = tinkerableTier;
        this.slots = slots;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var augments = AugmentUtils.getAugments(context.getStack());
        var success = false;

        for (var augment : augments) {
            if (augment.onItemUse(context))
                success = true;
        }

        if (success)
            return ActionResult.SUCCESS;

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        var stack = player.getStackInHand(hand);
        var augments = AugmentUtils.getAugments(stack);
        var success = false;

        for (var augment : augments) {
            if (augment.onRightClick(stack, world, player, hand))
                success = true;
        }

        if (success)
            return new TypedActionResult<>(ActionResult.SUCCESS, stack);

        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        var augments = AugmentUtils.getAugments(stack);
        var success = false;

        for (var augment : augments) {
            if (augment.onRightClickEntity(stack, player, target, hand))
                success = true;
        }

        return success ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var augments = AugmentUtils.getAugments(stack);
        var success = false;

        for (var augment : augments) {
            if (augment.onHitEntity(stack, target, attacker))
                success = true;
        }

        return success;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        var augments = AugmentUtils.getAugments(stack);
        var success =  super.postMine(stack, world, state, pos, entity);

        for (var augment : augments) {
            if (augment.onBlockDestroyed(stack, world, state, pos, entity))
                success = true;
        }

        return success;
    }

    @Override
    public int getRadius(ItemStack stack) {
        var augments = AugmentUtils.getAugments(stack);

        for (var augment : augments) {
            if (augment instanceof MiningAOEAugment)
                return ((MiningAOEAugment) augment).getRange();
        }
        return 0;
    }

    @Override
    public boolean playBreakEffects() {
        return false;
    }

    @Override
    public boolean attemptBreak(World world, BlockPos pos, PlayerEntity player, int breakRadius, BlockProcessor processor) {
        if (getRadius(player.getMainHandStack()) > 0) {
            return MagnaTool.super.attemptBreak(world, pos, player, breakRadius, processor);
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        AugmentUtils.getAugments(stack).forEach(a -> a.onInventoryTick(stack, world, entity, slot, isSelected));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));
        AugmentUtils.getAugments(stack).forEach(a -> {
            tooltip.add(a.getDisplayName().formatted(Formatting.GRAY));
        });
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return ModConfigs.ENCHANTABLE_SUPREMIUM_TOOLS.get() || super.isEnchantable(stack);
    }

    @Override
    public int getAugmentSlots() {
        return this.slots;
    }

    @Override
    public EnumSet<AugmentType> getAugmentTypes() {
        return TYPES;
    }

    @Override
    public int getTinkerableTier() {
        return this.tinkerableTier;
    }
}
