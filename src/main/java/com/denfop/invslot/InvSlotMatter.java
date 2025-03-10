package com.denfop.invslot;

import com.denfop.IUItem;
import com.denfop.Ic2Items;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvSlotMatter extends InvSlot {

    private int stackSizeLimit;

    public InvSlotMatter(TileEntityInventory base1) {
        super(base1, "input2", InvSlot.Access.I, 9, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 4;
    }

    public boolean accepts(ItemStack itemStack) {
        return (itemStack
                .getItem()
                .equals(Item.getItemFromBlock(IUItem.machines)) && itemStack.getItemDamage() <= 3) || (itemStack.isItemEqual(
                Ic2Items.massFabricator));
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    public double getMattercostenergy(ItemStack stack) {
        int count = stack.getItemDamage();
        if (stack.getItem().equals(Item.getItemFromBlock(IUItem.machines))) {
            switch (count) {
                case 1:
                    return 900000;
                case 2:
                    return 800000;
                case 3:
                    return 700000;
            }
        }
        return 1000000;
    }

    public double getMatterenergy(ItemStack stack) {
        int count = stack.getItemDamage();
        if (stack.getItem().equals(Item.getItemFromBlock(IUItem.machines))) {
            switch (count) {
                case 1:
                    return 8000000;
                case 2:
                    return 64000000;
                case 3:
                    return 256000000;
            }
        }
        return 1000000;
    }

    public double getMaxEnergy(InvSlotMatter inputSlot) {
        double maxEnergy = 0;
        for (int i = 0; i < 9; i++) {
            if (inputSlot.get(i) != null) {
                maxEnergy += (getMatterenergy(inputSlot.get(i)) * inputSlot.get(i).getCount());
            }

        }
        return maxEnergy;
    }

    public double getcostEnergy(InvSlotMatter inputSlot) {
        double cost = 0;
        int k = 0;
        for (int i = 0; i < 9; i++) {
            if (inputSlot.get(i) != null) {
                cost += (getMattercostenergy(inputSlot.get(i)) * inputSlot.get(i).getCount());
                k += (inputSlot.get(i).getCount());

            }

        }
        return cost / k;
    }

    public int getFluidTank(InvSlotMatter inputSlot) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (inputSlot.get(i) != null) {
                count += (getMattertank(inputSlot.get(i)) * inputSlot.get(i).getCount());
            }
        }
        return 1000 * count;
    }

    private int getMattertank(ItemStack itemStack) {
        int count = itemStack.getItemDamage();
        if (itemStack.getItem().equals(Item.getItemFromBlock(IUItem.machines))) {
            switch (count) {
                case 1:
                    return 12;
                case 2:
                    return 14;
                case 3:
                    return 16;
            }
        }
        return 10;
    }

}
