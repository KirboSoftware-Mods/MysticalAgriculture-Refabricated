package com.alex.mysticalagriculture.client.screen;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.screenhandler.SoulExtractorScreenHandler;
import com.alex.mysticalagriculture.cucumber.client.screen.BaseHandledScreen;
import com.alex.mysticalagriculture.cucumber.client.screen.widget.EnergyBarWidget;
import com.alex.mysticalagriculture.cucumber.util.Formatting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SoulExtractorScreen extends BaseHandledScreen<SoulExtractorScreenHandler> {
    private static final Identifier BACKGROUND = new Identifier(MysticalAgriculture.MOD_ID, "textures/gui/soul_extractor.png");
    private SoulExtractorBlockEntity block;

    public SoulExtractorScreen(SoulExtractorScreenHandler container, PlayerInventory inv, Text title) {
        super(container, inv, title, BACKGROUND, 176, 194);
    }

    @Override
    protected void init() {
        super.init();

        int x = this.x;
        int y = this.y;

        this.block = this.getBlockEntity();

        if (this.block != null) {
            this.addDrawableChild(new EnergyBarWidget(x + 7, y + 17, this.block.getEnergy(), this));
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        var title = this.getTitle().getString();

        this.textRenderer.draw(matrices, title, (float) (this.backgroundWidth / 2 - this.textRenderer.getWidth(title) / 2), 6.0F, 4210752);
        this.textRenderer.draw(matrices, this.playerInventoryTitle, 8.0F, (float) (this.backgroundHeight - 96 + 2), 4210752);

        // TODO: temporary workaround for dynamic energy storage
        if (this.block != null) {
            var tier = this.block.getMachineTier();
            var energy = this.block.getEnergy();

            energy.resetMaxEnergyStorage();

            if (tier != null) {
                energy.setMaxEnergyStorage((int) (this.block.getEnergy().getCapacity() * tier.getFuelCapacityMultiplier()));
            }
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        int x = this.x;
        int y = this.y;

        if (this.getFuelItemValue() > 0) {
            int lol = this.getBurnLeftScaled(13);
            this.drawTexture(matrices, x + 31, y + 52 - lol, 176, 12 - lol, 14, lol + 1);
        }

        if (this.getProgress() > 0) {
            int i2 = this.getProgressScaled(24);
            this.drawTexture(matrices, x + 98, y + 51, 176, 14, i2 + 1, 16);
        }
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack stack, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;

        super.drawMouseoverTooltip(stack, mouseX, mouseY);

        if (mouseX > x + 30 && mouseX < x + 45 && mouseY > y + 39 && mouseY < y + 53) {
            var text = Text.literal(Formatting.energy(this.getFuelLeft()).getString());
            this.renderTooltip(stack, text, mouseX, mouseY);
        }
    }

    private SoulExtractorBlockEntity getBlockEntity() {
        var world = this.client.world;

        if (world != null) {
            var block = world.getBlockEntity(this.getScreenHandler().getBlockPos());

            if (block instanceof SoulExtractorBlockEntity extractor) {
                return extractor;
            }
        }

        return null;
    }

    public int getProgress() {
        if (this.block == null)
            return 0;

        return this.block.getProgress();
    }

    public int getOperationTime() {
        if (this.block == null)
            return 0;

        var tier = this.block.getMachineTier();
        if (tier == null)
            return this.block.getOperationTime();

        return (int) (this.block.getOperationTime() * tier.getOperationTimeMultiplier());
    }

    public int getFuelLeft() {
        if (this.block == null)
            return 0;

        return this.block.getFuelLeft();
    }

    public int getFuelItemValue() {
        if (this.block == null)
            return 0;

        return this.block.getFuelItemValue();
    }

    public int getProgressScaled(int pixels) {
        int i = this.getProgress();
        int j = this.getOperationTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public int getBurnLeftScaled(int pixels) {
        int i = this.getFuelLeft();
        int j = this.getFuelItemValue();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }
}