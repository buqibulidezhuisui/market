package com.market.domain;



import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {


    public static final Long ROLE_ADMIN= 1L;
    public static final Long ROLE_USER = 2L;


    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
