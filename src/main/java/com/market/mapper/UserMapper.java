package com.market.mapper;

import com.market.domain.Authority;
import com.market.domain.User;
import com.market.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    public  User findByUserName(String username);

    public List<Authority> findByUserId(Long userId);

    public User findUserById(Long id);

    public void deleteUserById(Long id);

    public User findUserByOpenid(String openid);
}