package com.market.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.config.ParamCommon;
import com.market.domain.Authority;
import com.market.domain.User;
import com.market.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveT(User user) {
        return userMapper.saveT(user);
    }

    @Override
    public PageInfo<User> findAll(User user, int pageNum) {
        PageHelper.startPage(pageNum, ParamCommon.QUERY_PAGE_SIZE);
        List<User> list = userMapper.findAll(user);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        list = list.stream().map(e -> {
            List<Authority> authorities = userMapper.findByUserId(e.getId());
            e.setAuthorities(authorities);
            return e;
        }).collect(Collectors.toList());;
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public User findT(User user) {
        return userMapper.findT(user);
    }

    @Override
    public void removeT(User user) {

    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User findUserById(Long id) {
        User user = userMapper.findUserById(id);
        List<Authority> authorities = userMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }
}
