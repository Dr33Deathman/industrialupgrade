package com.denfop.api;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

/**
 * Recipe manager interface for basic machines.
 *
 * @author RichardG, Player
 */
public interface IMolecularTransformatorRecipeManager { // TODO: merge with IMachineRecipeManagerExt

    /**
     * Adds a recipe to the machine.
     *
     * @param input    Recipe input
     * @param metadata meta data for additional recipe properties, may be null
     * @param outputs  Recipe outputs, zero or more depending on the machine
     *                 <p>
     *                 For the thermal centrifuge   @param metadata meta data {"minHeat": 1-xxx}
     *                 For the ore washing plant  @param metadata meta data  {"amount": 1-8000}
     */
    void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs);

    /**
     * Gets the recipe output for the given input.
     *
     * @param input       Recipe input
     * @param adjustInput modify the input according to the recipe's requirements
     * @return Recipe output, or null if none
     */
    RecipeOutput getOutputFor(ItemStack input, boolean adjustInput);

    /**
     * Gets a list of recipes.
     * <p>
     * You're a mad evil scientist if you ever modify this.
     *
     * @return List of recipes
     */
    Map<IRecipeInput, RecipeOutput> getRecipes(); // TODO: Change to iterable/collection/list

}
