package com.example.socks.mapper;

import com.example.socks.dto.StoreKeeperDto;
import com.example.socks.model.StoreKeeper;

public class StoreKeeperMapper {
    public StoreKeeperDto storeKeeperDto(StoreKeeper storeKeeper) {
        StoreKeeperDto storeKeeperDto = new StoreKeeperDto();
        storeKeeperDto.setId(storeKeeper.getId());
        storeKeeperDto.setPhone(storeKeeper.getPhone());
        storeKeeperDto.setFirstName(storeKeeper.getFirstName());
        storeKeeperDto.setLastName(storeKeeper.getLastName());
        storeKeeperDto.setUserName(storeKeeper.getUserName());
        return storeKeeperDto;
    }
}
