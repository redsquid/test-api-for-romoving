package org.example.service;

import org.example.repositury.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    private final int recordsPerIteration;

    private final ExecutorService executor;

    private final RecordRepository repo;

    public RecordService(
            @Value("${app.removing-records-per-iteration}") int recordsPerIteration,
            @Value("${app.threads-for-removing}") int threads,
            RecordRepository repo) {

        this.repo = repo;
        this.recordsPerIteration = recordsPerIteration;
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public void createRecord(LocalDateTime dateTime) {
        repo.createRecord(dateTime);
        logger.info("record has been created");
    }

    public void startDeletingProcess(LocalDateTime dateTime) {
        executor.submit(() -> {
            int total = 0;
            int n;
            do {
                n = repo.deleteAllBefore(dateTime, recordsPerIteration);
                logger.info("{} records has been deleted", n);
                total += n;
            } while (n != 0);
            logger.info("Removing process is finished. {} records have been deleted", total);
        });
    }
}
