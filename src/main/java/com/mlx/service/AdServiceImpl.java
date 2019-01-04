package com.mlx.service;

import com.mlx.domain.entities.Ad;
import com.mlx.domain.entities.Category;
import com.mlx.domain.entities.Photo;
import com.mlx.domain.models.binding.AdSubmitBindingModel;
import com.mlx.repository.AdRepository;
import com.mlx.repository.CategoryRepository;
import com.mlx.repository.PhotoRepository;
import com.mlx.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    private final PhotoRepository photoRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final CloudService cloudService;

    @Autowired
    public AdServiceImpl(AdRepository adRepository, PhotoRepository photoRepository, CategoryRepository categoryRepository, UserRepository userRepository, ModelMapper modelMapper, CloudService cloudService) {
        this.adRepository = adRepository;
        this.photoRepository = photoRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
    }

    @Override
    public void create(AdSubmitBindingModel bindingModel, Principal principal) {
        Ad ad = this.modelMapper.map(bindingModel, Ad.class);

        for (String categoryId : bindingModel.getCategoryIds()) {
            Optional<Category> categoryOptional = this.categoryRepository.findById(Integer.parseInt(categoryId));
            if (categoryOptional.isPresent()) {
                ad.addCategory(categoryOptional.get());
            }
        }
        ad.setAuthor(this.userRepository.findByUsername(principal.getName()));

        this.adRepository.saveAndFlush(ad);
        for (MultipartFile multipartFile : bindingModel.getPhotos()) {
            try {
                Photo photo = new Photo();
                photo.setAd(ad);
                photo.setUrl(this.cloudService.upload(multipartFile));

                this.photoRepository.saveAndFlush(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Ad> findAll() {
        return this.adRepository.findAll();
    }
}
