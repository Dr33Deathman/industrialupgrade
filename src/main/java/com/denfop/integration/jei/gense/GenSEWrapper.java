package com.denfop.integration.jei.gense;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenSEWrapper implements IRecipeWrapper {


    private final ItemStack outputstack;


    public GenSEWrapper(GenSEHandler container) {


        this.outputstack = container.getOutput();

    }

    public List<ItemStack> getOutputs() {
        return new ArrayList(Collections.singleton(this.outputstack));
    }


    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(ItemStack.class, this.getOutputs());
    }


    public ItemStack getOutput() {
        return outputstack;
    }

    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

}
