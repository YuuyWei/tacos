package com.yuuy.tacos.controllers;

import com.yuuy.tacos.entities.Order;
import com.yuuy.tacos.entities.Taco;
import com.yuuy.tacos.entities.Ingredient;
import com.yuuy.tacos.repositories.IngredientRepository;
import com.yuuy.tacos.repositories.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,
                                TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Model model,
                                @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            List<Ingredient> ingredients = ingredientRepository.findAll();

            Ingredient.Type[] types = Ingredient.Type.values();
            for (Ingredient.Type x : types) {
                model.addAttribute(x.toString().toLowerCase(),
                        ingredients.stream().filter(p -> p.getType().equals(x)).collect(Collectors.toList()));
            }
            for (ObjectError error : errors.getAllErrors()) {
                log.info(String.valueOf(error));
            }
            return "design";
        }

        Taco saved = tacoRepository.save(design);

        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type :
                types) {
            model.addAttribute(type.toString().toLowerCase(Locale.ROOT),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
