package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.EnumMultiMachine;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.init.Localization;

public class TileEntityDoubleMacerator extends TileEntityMultiMachine {

    public TileEntityDoubleMacerator() {
        super(
                EnumMultiMachine.DOUBLE_MACERATOR.usagePerTick,
                EnumMultiMachine.DOUBLE_MACERATOR.lenghtOperation,
                Recipes.macerator,
                0
        );
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", 2, Recipes.macerator);
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.DOUBLE_MACERATOR;
    }

    public String getInventoryName() {
        return Localization.translate("iu.blockMacerator.name");
    }

    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }


}
