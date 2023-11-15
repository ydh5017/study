package com.project.blog.config.jwt;

import com.project.blog.vo.UserVO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserVO userVO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        userVO.getRolesList().forEach(r -> {
            authorities.add(() -> r);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return userVO.getPassword();
    }

    @Override
    public String getUsername() {
        return userVO.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
