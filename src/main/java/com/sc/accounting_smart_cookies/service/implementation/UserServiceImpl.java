package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.BaseEntityListener;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.entity.common.UserPrincipal;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.UserRepository;
import com.sc.accounting_smart_cookies.service.SecurityService;
import com.sc.accounting_smart_cookies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MapperUtil mapperUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDTO findByUsername(String username) {
       User user=userRepository.findByUsername(username);
       return mapperUtil.convert(user,new UserDTO());
    }

    @Override
    public UserDTO findById(Long id) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new NoSuchElementException("User can not found")
        );
        return mapperUtil.convert(user,new UserDTO() );
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {

        User user1 = userRepository.findByUsername(dto.getUsername());

        User convertedUser = mapperUtil.convert(dto, new User());

        convertedUser.setId(user1.getId());

        convertedUser.setPassword(passwordEncoder.encode(user1.getPassword()));

        userRepository.save(convertedUser);

        return mapperUtil.convert(convertedUser,new UserDTO());
    }

    @Override
    public void save(UserDTO dto) {
        dto.setEnabled(true);
        User user =mapperUtil.convert(dto,new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void delete(Long id) {
        User user=userRepository.findByIdAndIsDeleted(id,false);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User>userList=userRepository.findAllByIsDeleted(false);
        return userList.stream().map(user -> mapperUtil.convert(user,new UserDTO())).
                collect(Collectors.toList());
    }
}
