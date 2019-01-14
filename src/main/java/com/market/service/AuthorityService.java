package com.market.service;

import com.market.domain.Authority;
import com.market.domain.UserAuthority;

public interface AuthorityService {
    public Authority getAuthorityById(Long id);

    public void saveUserAuthority(UserAuthority userAuthority);

    public void deleteUserAuthority(long userId);
}
