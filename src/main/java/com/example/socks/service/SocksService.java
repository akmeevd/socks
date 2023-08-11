package com.example.socks.service;

import com.example.socks.model.Batch;
import com.example.socks.model.Socks;
import com.example.socks.model.SocksId;
import com.example.socks.repository.BatchRepository;
import com.example.socks.repository.SocksRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service for working with socks
 * creating batches of incoming and outcoming socks in db
 * getting sum socks with certain color and cotton part
 */
@Service
public class SocksService {
    private final Logger logger = LoggerFactory.getLogger(SocksService.class);
    private final SocksRepository socksRepository;
    private final BatchRepository batchRepository;

    public SocksService(SocksRepository socksRepository, BatchRepository batchRepository) {
        this.socksRepository = socksRepository;
        this.batchRepository = batchRepository;
    }

    /**
     * method for creating incoming batch of socks in db
     * @param color
     * @param cottonPart
     * @param quantity
     * @return Socks
     * @throws RuntimeException
     */
    @Transactional
    public Socks income(String color, byte cottonPart, int quantity) {
        logger.info("creating incoming socks");
        return createBatchAndSocks(color, cottonPart, quantity, true);
    }

    /**
     * method for creating outcoming batch of socks in db
     * @param color
     * @param cottonPart
     * @param quantity
     * @return Socks
     * @throws RuntimeException
     */
    @Transactional
    public Socks outcome(String color, byte cottonPart, int quantity) {
        logger.info("creating outcoming socks");
        return createBatchAndSocks(color, cottonPart, quantity, false);
    }

    /**
     * private method is used public method income and outcome for class {@link SocksService}
     * @param color
     * @param cottonPart
     * @param quantity
     * @param isIncoming
     * @return Socks
     */
    private Socks createBatchAndSocks(String color, byte cottonPart, int quantity, boolean isIncoming) {
        validateQuantityAndCottonPart(cottonPart, quantity);
        SocksId socksId = new SocksId();
        socksId.setCottonPart(cottonPart);
        socksId.setColor(color);
        Batch batch = new Batch();
        batch.setIncoming(isIncoming);
        batch.setNumberSocks(quantity);
        Optional<Socks> foundSocks = socksRepository.findById(socksId);
        if (foundSocks.isEmpty()) {
            return saveNewSocks(socksId, batch, quantity);
        } else {
            return saveFoundSocks(foundSocks.get(), batch, quantity);
        }
    }

    /**
     * private method is used private method createBatchAndSocks
     * @param socksId
     * @param batch
     * @param quantity
     * @return Socks
     * @throws RuntimeException
     */
    private Socks saveNewSocks(SocksId socksId, Batch batch, int quantity) {
        if (batch.isIncoming()) {
            Socks socks = new Socks(socksId, quantity, List.of(batch));
            batchRepository.save(batch);
            return socksRepository.save(socks);
        } else {
            throw new RuntimeException("expected socks don't exist");
        }
    }

    /**
     * private method is used private method createBatchAndSocks
     * @param foundSocks
     * @param batch
     * @param quantity
     * @return Socks
     * @throws RuntimeException
     */
    private Socks saveFoundSocks(Socks foundSocks, Batch batch, int quantity) {
        List<Batch> batches = foundSocks.getBatch();
        batches.add(batch);
        foundSocks.setBatch(batches);
        if (batch.isIncoming()) {
            foundSocks.setQuantity(foundSocks.getQuantity() + quantity);
            batchRepository.save(batch);
            return socksRepository.save(foundSocks);

        } else {
            if (foundSocks.getQuantity() < quantity) {
                throw new RuntimeException("it's not quite socks");
            }
            foundSocks.setQuantity(foundSocks.getQuantity() - quantity);
            batchRepository.save(batch);
            return socksRepository.save(foundSocks);
        }

    }

    private void validateQuantityAndCottonPart(byte cottonPart, int quantity) {
        if (cottonPart < 0 || quantity < 0 || cottonPart > 100) {
            throw new RuntimeException("invalid data");
        }
    }

    public int getSocksNumberByColorAndCottonPart(String color, String operation, byte cottonPart) {
        logger.info("getting sum socks with color {} and cotton part {} {} ", color, operation, cottonPart);
        List<Socks> socks = switch (operation) {
            case "moreThan" -> socksRepository.findBySocksIdColorAndSocksIdCottonPartAfter(color, cottonPart);
            case "lessThan" -> socksRepository.findBySocksIdColorAndSocksIdCottonPartBefore(color, cottonPart);
            case "equal" -> socksRepository.findBySocksIdColorAndSocksIdCottonPart(color, cottonPart);
            default -> null;
        };
        if (socks.isEmpty()) {
            throw new RuntimeException("expected socks not found");
        } else {
            return socks.stream().map(Socks::getQuantity).mapToInt(Integer::intValue).sum();
        }
    }
}
