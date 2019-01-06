package com.mlx.service;

import com.mlx.domain.entities.Category;
import com.mlx.domain.exceptions.ResourceNotFoundException;
import com.mlx.domain.models.binding.CategoryCreateBindingModel;
import com.mlx.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void create(CategoryCreateBindingModel bindingModel) {
        Category category = this.modelMapper.map(bindingModel, Category.class);

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return categoryOptional.get();
    }

    @Override
    public void edit(CategoryCreateBindingModel bindingModel, Integer id) {
        Category category = this.findById(id);

        category.setName(bindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }
}
