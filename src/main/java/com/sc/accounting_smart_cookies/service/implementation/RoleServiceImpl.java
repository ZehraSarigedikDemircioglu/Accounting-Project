package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.RoleDTO;
import com.sc.accounting_smart_cookies.dto.UserDTO;
import com.sc.accounting_smart_cookies.entity.Role;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.RoleRepository;
import com.sc.accounting_smart_cookies.service.RoleService;
import com.sc.accounting_smart_cookies.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {


   private final RoleRepository roleRepository;
   private final MapperUtil mapperUtil;
   private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil,
                           @Lazy SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    public List<RoleDTO> getAllRoles() {
        List<Role>roleList=roleRepository.findAll();
        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role=roleRepository.findById(id).orElseThrow();
        return mapperUtil.convert(role, new RoleDTO());
    }

    @Override
    public List<RoleDTO> getAllRolesForCurrentUser() {
        UserDTO user=securityService.getLoggedInUser();
        if(user.getRole().getId()!=1){
            List<Role>roleList=roleRepository.getAllRoleForAdmin(user.getRole().getDescription());
            return roleList.stream().map(role1 -> mapperUtil.convert(role1, new RoleDTO()))
                    .collect(Collectors.toList());
        }
        else{
            Role role=roleRepository.getAllRoleForRoot(user.getRole().getDescription());
            return Collections.singletonList(mapperUtil.convert(role, new RoleDTO()));
        }
    }
}
