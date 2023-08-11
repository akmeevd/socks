package com.example.socks.security;

import com.example.socks.model.StoreKeeper;
import com.example.socks.service.StoreKeeperService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StoreKeeperService storeKeeperService;

    public UserDetailsServiceImpl(StoreKeeperService storeKeeperService) {
        this.storeKeeperService = storeKeeperService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoreKeeper storeKeeper = storeKeeperService.loadStoreKeeperByUsername(username) ;
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setStoreKeeper(storeKeeper);
        return userDetails;
    }

}
