package com.market.service;


import com.market.base.exception.DataAccessException;
import com.market.domain.Authority;
import com.market.domain.User;
import com.market.mapper.UserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Log
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名"+username+"不存在");
        }
        log.info("登录用户名:"+user.getUsername());
        List<Authority> authorities = userMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        return user;
    }
}
