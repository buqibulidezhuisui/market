package com.market.service;



import com.market.domain.User;

/**
 * The interface User service.
 *
 * @author Ruizhi
 * @date 2019.01.10
 */
public interface UserService extends BaseService<User> {
    /**
     * Find by user name user.
     *
     * @param username the username
     * @return the user
     */
    public  User findByUserName(String username);

    public User findUserById(Long id);

    public void deleteUserById(Long id);
}
