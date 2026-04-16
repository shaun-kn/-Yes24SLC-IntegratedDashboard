package knr.yes24slc.scheduler;

import knr.yes24slc.dto.OsrInventoryDto;
import knr.yes24slc.repository.OsrInventoryRepository;
import knr.yes24slc.service.OsrInventoryCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OsrInventoryScheduler {

    private final OsrInventoryRepository repository;
    private final OsrInventoryCache cache;

    public OsrInventoryScheduler(OsrInventoryRepository repository,
                                 OsrInventoryCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    @Scheduled(fixedDelay = 1000) // 1초
    public void refresh() {
        List<OsrInventoryDto> data = repository.findAll();
        cache.set(data);
        //System.out.println("cache updated: " + data.size());
    }
}