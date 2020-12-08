package com.denfop.ssp.items.armor;

import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;

import com.denfop.ssp.Configs;
import com.denfop.ssp.items.armorbase.ItemLeggins;
import com.denfop.ssp.keyboard.SSPKeys;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.world.World;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemArmorQuantumLeggins extends ItemLeggins
{
    protected static final int DEFAULT_COLOUR = -1;
    
    public ItemArmorQuantumLeggins() {
        super("graviLeggins", Configs.maxCharge5, Configs.transferLimit5, Configs.tier5);
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        return "super_solar_panels:textures/armour/" + this.name + ((type != null) ? "Overlay" : "") + ".png";
    }
    
    public void func_82813_b(final ItemStack stack, final int colour) {
        this.getDisplayNbt(stack, true).setInteger("colour", colour);
    }
    
    public boolean func_82816_b_(final ItemStack stack) {
        return this.func_82814_b(stack) != -1;
    }
    
    public int func_82814_b(final ItemStack stack) {
        final NBTTagCompound nbt = this.getDisplayNbt(stack, false);
        return (nbt == null || !nbt.hasKey("colour", 3)) ? -1 : nbt.getInteger("colour");
    }
    
    public void func_82815_c(final ItemStack stack) {
        final NBTTagCompound nbt = this.getDisplayNbt(stack, false);
        if (nbt == null || !nbt.hasKey("colour", 3)) {
            return;
        }
        nbt.removeTag("colour");
        if (nbt.hasNoTags()) {
            stack.getTagCompound().removeTag("display");
        }
    }
    
    protected NBTTagCompound getDisplayNbt(final ItemStack stack, final boolean create) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            if (!create) {
                return null;
            }
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        NBTTagCompound out;
        if (!nbt.hasKey("display", 10)) {
            if (!create) {
                return null;
            }
            out = new NBTTagCompound();
            nbt.setTag("display", (NBTBase)out);
        }
        else {
            out = nbt.getCompoundTag("display");
        }
        return out;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack stack) {
        return EnumRarity.EPIC;
    }
    
    @Override
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
        super.onArmorTick(world, player, stack);
        player.extinguish();
        final NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        byte toggleTimer = nbtData.getByte("toggleTimer");
        boolean ret = false;
        final boolean enableQuantumSpeedOnSprint = !IC2.platform.isRendering() || ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
        if (ElectricItem.manager.canUse(stack, 1000.0) && (player.onGround || player.isInWater()) && IC2.keyboard.isForwardKeyDown(player) && ((enableQuantumSpeedOnSprint && player.isSprinting()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) {
            byte speedTicker = nbtData.getByte("speedTicker");
            ++speedTicker;
            if (speedTicker >= 10) {
                speedTicker = 0;
                ElectricItem.manager.use(stack, 1000.0, null);
                ret = true;
            }
            nbtData.setByte("speedTicker", speedTicker);
            float speed = 0.22f;
            if (player.isInWater()) {
                speed = 0.12f;
                if (IC2.keyboard.isJumpKeyDown(player)) {
                    player.motionY += 0.12000000149011612;
                }
            }
            if (speed > 0.0f) {
                player.moveRelative(0.0f, 0.0f, 1.0f, speed);
            }
        }
        IC2.platform.profilerEndSection();
       
        
        boolean Nightvision = nbtData.getBoolean("Nightvision");
        short hubmode = nbtData.getShort("HudMode");
        if (SSPKeys.Isremovepoison(player) && toggleTimer == 0) {
            toggleTimer = 10;
            Nightvision = !Nightvision;
            if (IC2.platform.isSimulating()) {
                nbtData.setBoolean("Nightvision", Nightvision);
                if (Nightvision) {
                    IC2.platform.messagePlayer(player, "Effects enabled.", new Object[0]);
                }
                else {
                    IC2.platform.messagePlayer(player, "Effects disabled.", new Object[0]);
                }
            }
        }
        if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            if (hubmode == HudMode.getMaxMode()) {
                hubmode = 0;
            }
            else {
                ++hubmode;
            }
            if (IC2.platform.isSimulating()) {
                nbtData.setShort("HudMode", hubmode);
                IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
            }
        }
        if (IC2.platform.isSimulating() && toggleTimer > 0) {
            final NBTTagCompound nbtTagCompound = nbtData;
            final String s = "toggleTimer";
            --toggleTimer;
            nbtTagCompound.setByte(s, toggleTimer);
        }
        if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(stack, 1.0, (EntityLivingBase)player)) {
            final BlockPos pos = new BlockPos((int)Math.floor(player.posX), (int)Math.floor(player.posY), (int)Math.floor(player.posZ));
            final int skylight = player.getEntityWorld().getLightFromNeighbors(pos);
            if (skylight > 8) {
                
            	 if (Configs.canCraftHSP) {
            	        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 2, true, true));
            	        } else {
            	        	return;
            	        }
            	        
            	        if (Configs.canCraftHSH) {
            	        player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 300, 2, true, true));
            	        }else {
            	        	return;
            	        }
            }
            else {
               
            	 if (Configs.canCraftHSP) {
            	        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 2, true, true));
            	        } else {
            	        	return;
            	        }
            	        
            	        if (Configs.canCraftHSH) {
            	        player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 300, 2, true, true));
            	        }else {
            	        	return;
            	        }
            }  
            }
            ret = true;
        }
        
 
    
    
   
    
    @Override
    public float getPower(final ItemStack stack) {
        return 1.5f;
    }
    
    @Override
    public float getDropPercentage(final ItemStack stack) {
        return 0.01f;
    }
    
    @Override
    public float getBaseThrust(final ItemStack stack, final boolean hover) {
        return hover ? 1.0f : 0.5f;
    }
    
    @Override
    public float getBoostThrust(final EntityPlayer player, final ItemStack stack, final boolean hover) {
        return (IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 834.0) ? (hover ? 0.1f : 0.3f) : 0.0f;
    }
    
    @Override
    public boolean useBoostPower(final ItemStack stack, final float boostAmount) {
        return ElectricItem.manager.discharge(stack, 834.0, Integer.MAX_VALUE, true, false, false) > 0.0;
    }
    
    @Override
    public float getWorldHeightDivisor(final ItemStack stack) {
        return 0.91071427f;
    }
    
    @Override
    public float getHoverMultiplier(final ItemStack stack, final boolean upwards) {
        return 0.25f;
    }
    
    @Override
    public float getHoverBoost(final EntityPlayer player, final ItemStack stack, final boolean up) {
        if (IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 834.0) {
            if (!player.onGround) {
                ElectricItem.manager.discharge(stack, 834.0, Integer.MAX_VALUE, true, false, false);
            }
            return 3.0f;
        }
        return 1.0f;
    }
    
    @Override
    public boolean drainEnergy(final ItemStack pack, final int amount) {
        return ElectricItem.manager.discharge(pack, 278.0, Integer.MAX_VALUE, true, false, false) > 0.0;
    }
    
    @Override
    public int getEnergyPerDamage() {
        return 20000;
    }
    
    @Override
    public double getDamageAbsorptionRatio() {
        return 1.1;
    }
    
    @Override
    public boolean canProvideEnergy(final ItemStack stack) {
        return true;
    }
}
