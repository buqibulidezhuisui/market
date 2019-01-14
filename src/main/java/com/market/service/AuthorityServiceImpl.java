package com.market.service;

import com.market.domain.Authority;
import com.market.domain.UserAuthority;
import com.market.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    public Authority getAuthorityById(Long id) {
        return authorityMapper.getAuthorityById(id);
    }

    public void saveUserAuthority(UserAuthority userAuthority) {
        authorityMapper.saveUserAuthority(userAuthority);
    }

    public void deleteUserAuthority(long userId) {
        authorityMapper.deleteUserAuthority(userId);
    }
}
