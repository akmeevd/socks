package com.example.socks.service;

import com.example.socks.dto.StoreKeeperDto;
import com.example.socks.mapper.StoreKeeperMapper;
import com.example.socks.model.StoreKeeper;
import com.example.socks.repository.StoreKeeperRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * service for working with storekeepers
 */
@Service
public class StoreKeeperService {

    private final Logger logger = LoggerFactory.getLogger(StoreKeeperService.class);
    private final StoreKeeperRepository storeKeeperRepository;
    private final PasswordEncoder encoder;
    private final Pattern pattern = Pattern.compile("^((\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{10}$");

    public StoreKeeperService(StoreKeeperRepository storeKeeperRepository, @Lazy PasswordEncoder encoder) {
        this.storeKeeperRepository = storeKeeperRepository;
        this.encoder = encoder;
    }

    /**
     * method for creating {@link StoreKeeper} and invoking him in db
     * @param storeKeeper
     * @return StoreKeeperDto
     * @throws RuntimeException
     */
    public StoreKeeperDto create(StoreKeeper storeKeeper) {
        logger.info("creating storeKeeper "  + storeKeeper);
        if (!validatePhone(storeKeeper.getPhone())) {
            throw new RuntimeException("invalid phone");
        }
        storeKeeper.setRole("USER");
        storeKeeper.setPassword(encoder.encode(storeKeeper.getPassword()));
        storeKeeper.setRegistrationDate(LocalDateTime.now());
        storeKeeperRepository.save(storeKeeper);
        StoreKeeperMapper storeKeeperMapper = new StoreKeeperMapper();
        return storeKeeperMapper.storeKeeperDto(storeKeeper);
    }

    /**
     * finding storekeeper in db by username
     * @param username
     * @return StoreKeepers
     */
    public StoreKeeper loadStoreKeeperByUsername(String username) {
        logger.info("loading user by username {}", username);
        return storeKeeperRepository.findByUserName(username);
    }

    /**
     * method for checking if phone valid or not
     * @param str phone number
     * @return boolean
     */
    private boolean validatePhone(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
