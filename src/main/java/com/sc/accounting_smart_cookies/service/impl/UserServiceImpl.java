package com.sc.accounting_smart_cookies.service.impl;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.UserRepository;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User can not found")
        );
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {

        User user1 = userRepository.findByUsername(dto.getUsername());

        User convertedUser = mapperUtil.convert(dto, new User());

        convertedUser.setId(user1.getId());

        convertedUser.setPassword(passwordEncoder.encode(user1.getPassword()));

        userRepository.save(convertedUser);

        return mapperUtil.convert(convertedUser, new UserDTO());
    }

    @Override
    public void save(UserDTO dto) {
        dto.setEnabled(true);
        User user = mapperUtil.convert(dto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAllByIsDeleted(false);
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO())).
                collect(Collectors.toList());
    }
}
