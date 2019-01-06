package com.mlx.service;

import com.mlx.domain.entities.Category;
import com.mlx.domain.models.binding.CategoryCreateBindingModel;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void create(CategoryCreateBindingModel bindingModel);

    Category findById(Integer id);

    void edit(CategoryCreateBindingModel bindingModel, Integer id);
}
