package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerStorageExp;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.init.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;


public class GUIStorageExp extends GuiIC2<ContainerStorageExp> {

    public final ContainerStorageExp container;

    public GUIStorageExp(ContainerStorageExp container1) {
        super(container1);
        this.container = container1;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 38, (this.height - this.ySize) / 2 + 61,
                74, 16, Localization.translate("button.xpremove")
        ));
        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + 38, (this.height - this.ySize) / 2 + 17,
                74, 16, Localization.translate("button.xpadd")
        ));

    }

    protected void actionPerformed(GuiButton guibutton) throws IOException {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get(false).initiateClientTileEntityEvent(this.container.base, 0);

        }
        if (guibutton.id == 1) {
            IC2.network.get(false).initiateClientTileEntityEvent(this.container.base, 1);

        }
    }

    protected void drawForegroundLayer(int par1, int par2) {
        super.drawForegroundLayer(par1, par2);
        this.fontRenderer.drawString("Lvl:" + this.container.base.expirencelevel, 100, 51 - 5, 4210752);
        this.fontRenderer.drawString("Lvl:" + this.container.base.expirencelevel1, 31, 51 - 5, 4210752);


    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int xoffset = (this.width - this.xSize) / 2;
        int yoffset = (this.height - this.ySize) / 2;
        this.mc.getTextureManager().bindTexture(getTexture());
        drawTexturedModalRect(xoffset, yoffset, 0, 0, this.xSize, this.ySize);
        String name = Localization.translate(this.container.base.getName());
        this.drawXCenteredString(this.xSize / 2, 6, name, 4210752, false);
        int chargeLevel = (int) (47.0F * this.container.base.storage
                / this.container.base.maxStorage);
        int chargeLevel1 = (int) (47.0F * this.container.base.storage1
                / this.container.base.maxStorage);
        chargeLevel = Math.min(chargeLevel, 47);
        chargeLevel1 = Math.min(chargeLevel1, 47);
        if (chargeLevel > 0) {
            drawTexturedModalRect(xoffset + 153, yoffset + 26 + 47 - chargeLevel, 180,
                    51 - chargeLevel, 12, chargeLevel
            );
        }

        if (chargeLevel1 > 0) {
            drawTexturedModalRect(xoffset + 11, yoffset + 26 + 47 - chargeLevel1, 180,
                    51 - chargeLevel1, 12, chargeLevel1
            );
        }

    }


    public ResourceLocation getTexture() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIExpStorage.png");
    }

}
