package com.market.mapper;

import com.market.domain.Authority;
import com.market.domain.UserAuthority;
import com.market.mapper.common.BaseMapper;

public interface AuthorityMapper {
    public Authority getAuthorityById(Long id);

    public void saveUserAuthority(UserAuthority userAuthority);

    public void deleteUserAuthority(long userId);
}
