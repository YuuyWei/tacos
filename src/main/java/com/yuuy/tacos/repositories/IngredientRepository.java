package com.yuuy.tacos.repositories;

import com.yuuy.tacos.entities.Ingredient;

import java.util.List;

public interface IngredientRepository {

    List<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);
}
