package com.Denfop.block.Base;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.block.TileEntityBlock;
import ic2.core.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.mutable.MutableObject;
import com.Denfop.SuperSolarPanels;
import com.Denfop.block.TileEntityDoubleMetalFormer.TileEntityDoubleMetalFormer;
import com.Denfop.block.TileEntityTripleMetalFormer.TileEntityTripleMetalFormer;
import com.Denfop.block.advancedmatter.TileEntityAdvancedMatter;
import com.Denfop.block.doublecompressor.TileEntityDoubleCompressor;
import com.Denfop.block.doubleelecfurnace.TileEntityDoubleElectricFurnace;
import com.Denfop.block.doubleextractor.TileEntityDoubleExtractor;
import com.Denfop.block.doublemacertator.TileEntityDoubleMacerator;
import com.Denfop.block.improvematter.TileEntityImprovedMatter;
import com.Denfop.block.mechanism.TileEntityAlloySmelter;
import com.Denfop.block.triplecompressor.TileEntityTripleCompressor;
import com.Denfop.block.tripleelecfurnace.TileEntityTripleElectricFurnace;
import com.Denfop.block.triplemacerator.TileEntityTripleMacerator;
import com.Denfop.block.ultimatematter.TileEntityUltimateMatter;
import com.Denfop.item.base.ItemElectricBlock;
import com.Denfop.proxy.ClientProxy;
import com.Denfop.tiles.ElectricalBase.TileEntityElectricMFE;
import com.Denfop.tiles.ElectricalBase.TileEntityElectricMFSU;
import com.Denfop.tiles.base.TileEntityBase;
import com.Denfop.tiles.base.TileEntityElectricBlock;
import com.Denfop.utils.InternalName;;


public class BlockElectric extends BlockContainer {
  public BlockElectric() {
    super(Material.iron);
    setHardness(1.5F);
    setStepSound(soundTypeMetal);
   this.setCreativeTab(SuperSolarPanels.tabssp);
    
  }
  @Override
  public TileEntity createTileEntity(World world, int meta) {
    switch (meta) {
    case 0:
 	   return new TileEntityElectricMFE();
       case 1:
    	   return new TileEntityElectricMFSU();
        	
    } 
    return null;
  }
  private IIcon[][] iconBuffer;
  @Override
  public void registerBlockIcons(final IIconRegister par1IconRegister) {
      this.iconBuffer = new IIcon[2][6];
      this.iconBuffer[1][0] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][1] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][2] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][3] = par1IconRegister.registerIcon("supersolarpanel:hv_transformer_bottomtop");
      this.iconBuffer[1][4] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[1][5] = par1IconRegister.registerIcon("supersolarpanel:blockmfsu2");
      this.iconBuffer[0][0] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][1] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][2] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][3] = par1IconRegister.registerIcon("supersolarpanel:blockChargepadMFSUtop");
      this.iconBuffer[0][4] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
      this.iconBuffer[0][5] = par1IconRegister.registerIcon("supersolarpanel:blockMFSU");
  }
  
  @Override
  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide)
  {
      int blockMeta = world.getBlockMetadata(x, y, z);
      TileEntity te = world.getTileEntity(x, y, z);
      int facing = (te instanceof TileEntityBlock) ? ((int) (((TileEntityBlock) te).getFacing())) : 0;

     
          return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];
  }

  @Override
  public IIcon getIcon(int blockSide, int blockMeta)
  {
      return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
  }

  @Override
  public TileEntity createNewTileEntity(World world, int i)
  {
      return null;
  }
 
 
  @Override
  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
  {
      ArrayList<ItemStack> dropList = super.getDrops(world, x, y, z, metadata, fortune);
      TileEntity te = world.getTileEntity(x, y, z);
      if (te instanceof IInventory)
      {
          IInventory iinv = (IInventory) te;
          for (int index = 0; index < iinv.getSizeInventory(); ++index)
          {
              ItemStack itemstack = iinv.getStackInSlot(index);
              if (itemstack != null)
              {
                  dropList.add(itemstack);
                  iinv.setInventorySlotContents(index, (ItemStack) null);
              }
          }
      }

      return dropList;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block blockID, int blockMeta)
  {
      super.breakBlock(world, x, y, z, blockID, blockMeta);
      boolean var5 = true;
      for (Iterator<ItemStack> iter = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).iterator(); iter.hasNext(); var5 = false)
      {
          ItemStack var7 = (ItemStack) iter.next();
          if (!var5)
          {
              if (var7 == null)
              {
                  return;
              }

              double var8 = 0.7D;
              double var10 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              double var12 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              double var14 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
              EntityItem var16 = new EntityItem(world, (double) x + var10, (double) y + var12, (double) z + var14, var7);
              var16.delayBeforeCanPickup = 10;
              world.spawnEntityInWorld(var16);
              return;
          }
      }
  }
  
  @Override
  public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
  {
      return IC2Items.getItem("advancedMachine").getItem();
  }
  @Override
  public int getDamageValue(World world, int x, int y, int z)
  {
      return world.getBlockMetadata(x, y, z); // advanced machine item meta
                                              // exactly equals the block meta
  }

  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
  {
      super.onBlockPlacedBy(world, x, y, z, player, stack);
      int heading = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      TileEntityElectricBlock te = (TileEntityElectricBlock) world.getTileEntity(x, y, z);
     
      NBTTagCompound nbttagcompound1 = SuperSolarPanels.getOrCreateNbtData(stack);
      double energy1 = nbttagcompound1.getDouble("energy");
      double energy2 = nbttagcompound1.getDouble("energy2");
      te.energy=energy1;
      te.energy2=energy2;
      switch (heading)
      {
      case 0:
          te.setFacing((short) 2);
          break;
      case 1:
          te.setFacing((short) 5);
          break;
      case 2:
          te.setFacing((short) 3);
          break;
      case 3:
          te.setFacing((short) 4);
          break;
      }
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
  {
      if (!entityPlayer.isSneaking())
      {
          entityPlayer.openGui(SuperSolarPanels.instance, 0, world, x, y, z);
          return true;
      }

      return false;
  }
  private boolean isActive(IBlockAccess iba, int x, int y, int z)
  {
      return ((TileEntityBlock) iba.getTileEntity(x, y, z)).getActive();
  }

  @Override
  public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
  {
      if (axis == ForgeDirection.UNKNOWN)
      {
          return false;
      }
      TileEntity tileEntity = worldObj.getTileEntity(x, y, z);

      if ((tileEntity instanceof IWrenchable))
      {
          IWrenchable te = (IWrenchable) tileEntity;

          int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();

          if (te.wrenchCanSetFacing(null, newFacing))
          {
              te.setFacing((short) newFacing);
          }
      }

      return false;
  }

  public int isProvidingWeakPower(World world, int x, int y, int z, int side) {
    TileEntity te = world.getTileEntity(x, y, z);
    if (!(te instanceof TileEntityElectricBlock))
      return 0; 
    return ((TileEntityElectricBlock)te).isEmittingRedstone() ? 15 : 0;
  }
  @Override
  public boolean canProvidePower() {
    return true;
  }
  
  

 
  @Override
  public boolean hasComparatorInputOverride() {
	    return true;
	  }
	  
	
  
  @SideOnly(Side.CLIENT)
  public EnumRarity getRarity(ItemStack stack) {
    return (stack.getItemDamage() == 0 || stack.getItemDamage() == 1) ? EnumRarity.uncommon : EnumRarity.common;
  }
  @Override
  public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
	  TileEntity te = world.getTileEntity(x, y, z);
    if (!(te instanceof TileEntityBlock))
      return 0; 
    TileEntityElectricBlock teb = (TileEntityElectricBlock)te;
    return (int)Math.round(Util.map(teb.energy, teb.maxStorage, 15.0D));
    
  }
 
}
