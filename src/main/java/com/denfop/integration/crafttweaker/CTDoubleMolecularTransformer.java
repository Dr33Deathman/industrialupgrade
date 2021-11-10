package com.denfop.integration.crafttweaker;

import com.denfop.api.IDoubleMolecularRecipeManager;
import com.denfop.api.Recipes;
import ic2.api.recipe.RecipeOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.mods.ic2.IC2RecipeInput;
import modtweaker2.helpers.InputHelper;
import modtweaker2.utils.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ZenClass("mods.industrialupgrade.DoubleMolecularTransformer")
public class CTDoubleMolecularTransformer {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container, IIngredient fill, double energy) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("energy", energy);
        MineTweakerAPI.apply(new AddMolecularIngredientAction(container, fill, output, tag));
    }

    private static class AddMolecularIngredientAction extends OneWayAction {
        private final IIngredient container;

        private final IIngredient fill;

        private final IItemStack output;
        private final NBTTagCompound nbt;

        public AddMolecularIngredientAction(IIngredient container, IIngredient fill, IItemStack output, NBTTagCompound nbt) {
            this.container = container;
            this.fill = fill;
            this.output = output;
            this.nbt = nbt;
        }

        public void apply() {
            Recipes.doublemolecular.addRecipe(
                    new IC2RecipeInput(this.container),
                    new IC2RecipeInput(this.fill),
                    this.nbt,

                    getItemStack(this.output));

        }

        public String describe() {
            return "Adding double molecular recipe " + this.container + " + " + this.fill + " => " + this.output;
        }

        public static ItemStack getItemStack(IItemStack item) {
            if (item == null) {
                return null;
            } else {
                Object internal = item.getInternal();
                if (!(internal instanceof ItemStack)) {
                    MineTweakerAPI.logError("Not a valid item stack: " + item);
                }

                return new ItemStack(((ItemStack) internal).getItem(), item.getAmount(), item.getDamage());
            }
        }

        public Object getOverrideKey() {
            return null;
        }

        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + ((this.container != null) ? this.container.hashCode() : 0);
            hash = 67 * hash + ((this.fill != null) ? this.fill.hashCode() : 0);
            hash = 67 * hash + ((this.output != null) ? this.output.hashCode() : 0);
            hash = 67 * hash + ((this.nbt != null) ? this.nbt.hashCode() : 0);
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            AddMolecularIngredientAction other = (AddMolecularIngredientAction) obj;
            if (!Objects.equals(this.container, other.container))
                return false;
            if (!Objects.equals(this.fill, other.fill))
                return false;
            if (!Objects.equals(this.nbt, other.nbt))
                return false;

            return Objects.equals(this.output, other.output);
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        LinkedHashMap<IDoubleMolecularRecipeManager.Input, RecipeOutput> recipes = new LinkedHashMap();

        for (Map.Entry<IDoubleMolecularRecipeManager.Input, RecipeOutput> iRecipeInputRecipeOutputEntry : Recipes.doublemolecular.getRecipes().entrySet()) {

            for (ItemStack stack : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (stack.isItemEqual(InputHelper.toStack(output))) {
                    recipes.put(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue());
                }
            }
        }

        MineTweakerAPI.apply(new CTDoubleMolecularTransformer.Remove(recipes));
    }

    private static class Remove extends BaseMapRemoval<IDoubleMolecularRecipeManager.Input, RecipeOutput> {
        protected Remove(Map<IDoubleMolecularRecipeManager.Input, RecipeOutput> recipes) {
            super("doublemolecular", Recipes.doublemolecular.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IDoubleMolecularRecipeManager.Input, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }
}
