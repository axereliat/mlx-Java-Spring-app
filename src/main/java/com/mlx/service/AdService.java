package com.mlx.service;

import com.mlx.domain.entities.Ad;
import com.mlx.domain.models.binding.AdSubmitBindingModel;

import java.security.Principal;
import java.util.List;

public interface AdService {

    void create(AdSubmitBindingModel bindingModel, Principal principal);

    List<Ad> findAll();

    Ad findById(Integer id);
}
