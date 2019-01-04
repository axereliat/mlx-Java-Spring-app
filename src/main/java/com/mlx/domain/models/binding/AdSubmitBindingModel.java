package com.mlx.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AdSubmitBindingModel {

    @NotEmpty(message = "Title field is required.")
    private String title;

    @NotNull(message = "You must select at least one category.")
    private String[] categoryIds;

    @NotEmpty(message = "Description field is required.")
    private String description;

    @NotNull(message = "Price field is required.")
    private BigDecimal price;

    @NotNull(message = "You must upload at least one photo.")
    private MultipartFile[] photos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile[] getPhotos() {
        return photos;
    }

    public void setPhotos(MultipartFile[] photos) {
        this.photos = photos;
    }
}
