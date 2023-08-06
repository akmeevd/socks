package com.example.socks.security;

import com.example.socks.model.StoreKeeper;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
public class UserDetailsImpl implements UserDetails {

    private StoreKeeper storeKeeper;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + storeKeeper.getRole());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return storeKeeper.getPassword();
    }

    @Override
    public String getUsername() {
        return storeKeeper.getUserName();
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
