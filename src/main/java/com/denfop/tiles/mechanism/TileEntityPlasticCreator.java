package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerPlasticCreator;
import com.denfop.gui.GUIPlasticCreator;
import com.denfop.invslot.InvSlotProcessablePlastic;
import com.denfop.tiles.base.TileEntityBasePlasticCreator;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.init.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityPlasticCreator extends TileEntityBasePlasticCreator {

    public TileEntityPlasticCreator() {
        super(1, 300, 1);
        this.inputSlotA = new InvSlotProcessablePlastic(this, "inputA", 2);
    }

    public static void init() {
        final IRecipeInputFactory input = ic2.api.recipe.Recipes.inputFactory;
        Recipes.plastic.addRecipe(input.forStack(IUItem.PolyethCell), input.forStack(IUItem.PolypropCell),
                new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(IUItem.plast)
        );
        Recipes.plastic.addRecipe(
                input.forStack(IUItem.PolypropCell),
                input.forStack(IUItem.PolyethCell),
                new FluidStack(FluidRegistry.WATER, 1000),
                new ItemStack(IUItem.plast)
        );

    }

    public String getInventoryName() {

        return Localization.translate("iu.blockPlasticCreator.name");
    }

    public int gaugeLiquidScaled(int i) {
        return this.getFluidTank().getFluidAmount() <= 0
                ? 0
                : this.getFluidTank().getFluidAmount() * i / this.getFluidTank().getCapacity();
    }


    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIPlasticCreator(new ContainerPlasticCreator(entityPlayer, this));

    }

    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
                UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing
        );
    }

}
