package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.EnumMultiMachine;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.core.init.Localization;

public class TileEntityDoubleFermer extends TileEntityMultiMachine {

    public TileEntityDoubleFermer() {
        super(EnumMultiMachine.DOUBLE_Fermer.usagePerTick, EnumMultiMachine.DOUBLE_Fermer.lenghtOperation,
                Recipes.fermer, 3
        );
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.fermer);
    }


    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.DOUBLE_Fermer;
    }

    public String getInventoryName() {
        return Localization.translate("iu.blockFermer1.name");
    }

    public String getStartSoundFile() {
        return "Machines/Fermer.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }


}
