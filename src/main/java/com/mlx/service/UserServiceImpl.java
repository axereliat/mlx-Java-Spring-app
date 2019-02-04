package com.mlx.service;

import com.mlx.common.enumerations.Gender;
import com.mlx.domain.entities.Role;
import com.mlx.domain.entities.User;
import com.mlx.domain.models.binding.UserRegisterBindingModel;
import com.mlx.repository.RoleRepository;
import com.mlx.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegisterBindingModel bindingModel) {
        User user = this.modelMapper.map(bindingModel, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setGender(Gender.valueOf(bindingModel.getGender().toUpperCase()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        user.setDateOfBirth(LocalDate.parse(bindingModel.getBirthdate(), formatter));

        if (this.roleRepository.findAll().size() == 0) {
            Role userRole = new Role();
            userRole.setAuthority("USER");
            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");

            this.roleRepository.saveAndFlush(userRole);
            this.roleRepository.saveAndFlush(adminRole);
        }

        Set<Role> roles = new HashSet<>();
        if (this.userRepository.findAll().size() == 0) {
            roles.add(this.roleRepository.findByAuthority("ADMIN"));
        }
        roles.add(this.roleRepository.findByAuthority("USER"));

        user.setAuthorities(roles);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("Wrong username");
        }

        return user;
    }
}
