package com.sc.accounting_smart_cookies.service.implementation;

import com.sc.accounting_smart_cookies.dto.RoleDTO;
import com.sc.accounting_smart_cookies.entity.Role;
import com.sc.accounting_smart_cookies.mapper.MapperUtil;
import com.sc.accounting_smart_cookies.repository.RoleRepository;
import com.sc.accounting_smart_cookies.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MapperUtil mapperUtil;
    @Override
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
}
