package mekanism.client.gui;

import java.util.Arrays;
import mekanism.client.gui.element.GuiEnergyInfo;
import mekanism.client.gui.element.tab.GuiMatrixTab;
import mekanism.client.gui.element.tab.GuiMatrixTab.MatrixTab;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.inventory.container.ContainerInductionMatrix;
import mekanism.common.tile.TileEntityInductionCasing;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.common.util.TextComponentUtil;
import mekanism.common.util.TextComponentUtil.EnergyDisplay;
import mekanism.common.util.TextComponentUtil.Translation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiInductionMatrix extends GuiMekanismTile<TileEntityInductionCasing> {

    public GuiInductionMatrix(PlayerInventory inventory, TileEntityInductionCasing tile) {
        super(tile, new ContainerInductionMatrix(inventory, tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(new GuiMatrixTab(this, tileEntity, MatrixTab.STAT, resource));
        addGuiElement(new GuiEnergyInfo(() -> Arrays.asList(
              TextComponentUtil.build(Translation.of("mekanism.gui.storing"), ": ", EnergyDisplay.of(tileEntity.getEnergy(), tileEntity.getMaxEnergy())),
              TextComponentUtil.build(Translation.of("mekanism.gui.input"), ": ", EnergyDisplay.of(tileEntity.getLastInput()), "/t"),
              TextComponentUtil.build(Translation.of("mekanism.gui.output"), ": ", EnergyDisplay.of(tileEntity.getLastOutput()), "/t")
        ), this, resource));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        font.drawString(tileEntity.getName(), (xSize / 2) - (font.getStringWidth(tileEntity.getName()) / 2), 6, 0x404040);
        font.drawString(LangUtils.localize("container.inventory"), 8, (ySize - 94) + 2, 0x404040);
        font.drawString(LangUtils.localize("gui.input") + ":", 53, 26, 0x00CD00);
        font.drawString(MekanismUtils.getEnergyDisplay(tileEntity.getLastInput()) + "/t", 53, 35, 0x00CD00);
        font.drawString(LangUtils.localize("gui.output") + ":", 53, 44, 0x00CD00);
        font.drawString(MekanismUtils.getEnergyDisplay(tileEntity.getLastOutput()) + "/t", 53, 53, 0x00CD00);
        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;
        if (xAxis >= 7 && xAxis <= 39 && yAxis >= 14 && yAxis <= 72) {
            displayTooltip(MekanismUtils.getEnergyDisplay(tileEntity.getEnergy(), tileEntity.getMaxEnergy()), xAxis, yAxis);
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(int xAxis, int yAxis) {
        super.drawGuiContainerBackgroundLayer(xAxis, yAxis);
        if (tileEntity.getScaledEnergyLevel(58) > 0) {
            displayGauge(7, 14, tileEntity.getScaledEnergyLevel(58), 0);
            displayGauge(23, 14, tileEntity.getScaledEnergyLevel(58), 1);
        }
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return MekanismUtils.getResource(ResourceType.GUI, "GuiInductionMatrix.png");
    }

    public void displayGauge(int xPos, int yPos, int scale, int side /*0-left, 1-right*/) {
        int start = 0;
        while (true) {
            int renderRemaining;
            if (scale > 16) {
                renderRemaining = 16;
                scale -= 16;
            } else {
                renderRemaining = scale;
                scale = 0;
            }
            minecraft.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            drawTexturedRectFromIcon(guiLeft + xPos, guiTop + yPos + 58 - renderRemaining - start, MekanismRenderer.energyIcon, 16, renderRemaining);
            start += 16;
            if (renderRemaining == 0 || scale == 0) {
                break;
            }
        }
        minecraft.textureManager.bindTexture(getGuiLocation());
        drawTexturedRect(guiLeft + xPos, guiTop + yPos, 176, side == 0 ? 0 : 54, 16, 54);
    }
}