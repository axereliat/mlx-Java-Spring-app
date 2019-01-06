package com.mlx.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class CategoryCreateBindingModel {

    @NotEmpty(message = "Name field is required.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
