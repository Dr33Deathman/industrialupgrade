package com.Denfop.tiles.Mechanism;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.upgrade.UpgradableProperty;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.Denfop.SSPItem;
import com.Denfop.SuperSolarPanels;
import com.Denfop.InvSlot.InvSlotProcessableAlloy;
import com.Denfop.RecipeManager.AlloyRecipeManager;
import com.Denfop.api.Recipes;
import com.Denfop.container.ContainerStandardMachine;
import com.Denfop.gui.GuiAlloySmelter;
import com.Denfop.tiles.base.TileEntityBaseAlloySmelter;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TileEntityAlloySmelter extends TileEntityBaseAlloySmelter {

public TileEntityAlloySmelter() {
    super(1, 300, 1);
    this.inputSlotA = (InvSlotProcessable)new InvSlotProcessableAlloy(this, "inputA", 0, 1, Recipes.Alloysmelter);
    this.inputSlotB = (InvSlotProcessable)new InvSlotProcessableAlloy(this, "inputB", 1, 1, Recipes.Alloysmelter); 
  }
  
  public static void init() {
	  Recipes.Alloysmelter = new AlloyRecipeManager();
	  addAlloysmelter((IRecipeInput)new RecipeInputItemStack(new ItemStack(Items.iron_ingot), 1), (IRecipeInput)new RecipeInputItemStack(new ItemStack(Items.coal), 1),new ItemStack(Ic2Items.advIronIngot.getItem(),1,3) );
	addAlloysmelter((IRecipeInput)new RecipeInputItemStack( new ItemStack(Items.gold_ingot), 1),(IRecipeInput)new RecipeInputItemStack(Ic2Items.silverIngot, 1),new ItemStack(SSPItem.electriumingot,1) );
		addAlloysmelter((IRecipeInput)new RecipeInputItemStack(new ItemStack(SSPItem.nickel), 1), (IRecipeInput)new RecipeInputItemStack(new ItemStack(Items.iron_ingot), 1), new ItemStack(SSPItem.invaringot) );
  }
  
  public String getInventoryName() {
	  
    return "Aloy Smelter";
  }
  
  public static void addAlloysmelter(IRecipeInput container, IRecipeInput fill, ItemStack output) {
	    Recipes.Alloysmelter.addRecipe(container, fill, output);
	    Recipes.Alloysmelter.addRecipe(fill, container, output);
	  }
  @SideOnly(Side.CLIENT)
  public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
    return (GuiScreen)new GuiAlloySmelter(new ContainerStandardMachine(entityPlayer, this));
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
    return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
  }
  
  public static List<Map.Entry<ItemStack, ItemStack>> recipes = new Vector<Map.Entry<ItemStack, ItemStack>>();
}
