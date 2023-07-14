package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.exceptions.UserNotFoundException;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.CompanyRepository;
import com.sc.accounting_smart_cookies.repository.UserRepository;
import com.sc.accounting_smart_cookies.service.UserService;
import com.sc.accounting_smart_cookies.util.PhoneNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PhoneNumberUtils phoneNumberUtils;

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;


    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil,
                           PasswordEncoder passwordEncoder, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;


    }


    @Override
    public boolean findByUsername1(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;

    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                // () -> new NoSuchElementException("User can not be found")
                () -> new UserNotFoundException("This user can not be found in the system")
        );
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        User user1 = userRepository.findById(dto.getId()).orElseThrow();
        user1.setUsername(user1.getUsername());
        userRepository.save(user1);
        User convertedUser = mapperUtil.convert(dto, new User());

        convertedUser.setId(user1.getId());

        convertedUser.setPassword(passwordEncoder.encode(user1.getPassword()));

        convertedUser.setPhone(PhoneNumberUtils.formatPhoneNumber(convertedUser.getPhone(),
                getCountryAbbreviation(dto.getCompany().getAddress().getCountry())));

        userRepository.save(convertedUser);
        return mapperUtil.convert(convertedUser, new UserDTO());

    }

    @Override
    public void save(UserDTO dto) {
        dto.setEnabled(true);
        User user = mapperUtil.convert(dto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhone(PhoneNumberUtils.formatPhoneNumber(user.getPhone(),
                getCountryAbbreviation(dto.getCompany().getAddress().getCountry())));
        userRepository.save(user);

    }

    public String getCountryAbbreviation(String countryName) {
        String countryCode = "";

        for (String availableLocale : Locale.getISOCountries()) {
            Locale locale = new Locale("", availableLocale);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                countryCode = locale.getCountry();
                break;
            }
        }

        return countryCode;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + " "+user.getId());
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User LoggedInUser = userRepository.findByUsername(auth.getName());
        if (LoggedInUser.getId() != 1) {
            Company company = companyRepository.findById(LoggedInUser.getCompany().getId()).orElseThrow();

            List<User> userList = userRepository.listAllUserWithCompanyAndIsDeleted(company, false);
            return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO())).
                    collect(Collectors.toList());
        } else {
            List<User> userList = userRepository.findAllAdminRole("Admin");
            return userList.stream()
                    .map(user ->mapperUtil.convert(user,new UserDTO()) )
                    .peek(dto->dto.setOnlyAdmin(isOnlyAdmin(dto)))
                    .collect(Collectors.toList());

        }


    }

    private boolean isOnlyAdmin(UserDTO userDTO) {
        User user=mapperUtil.convert(userDTO,new User());
        Integer userOnlyAdmin = userRepository.isUserOnlyAdmin(user.getCompany(),user.getRole());
        return userOnlyAdmin == 1;

    }
}
