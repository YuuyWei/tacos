package com.yuuy.tacos.repositories;

import com.yuuy.tacos.entities.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
