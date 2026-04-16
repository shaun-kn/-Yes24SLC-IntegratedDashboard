package knr.yes24slc.service;

import knr.yes24slc.dto.OsrInventoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OsrInventoryCache {

    private List<OsrInventoryDto> cache;

    public synchronized void set(List<OsrInventoryDto> data) {
        this.cache = data;
    }

    public List<OsrInventoryDto> get() {
        return cache;
    }
}