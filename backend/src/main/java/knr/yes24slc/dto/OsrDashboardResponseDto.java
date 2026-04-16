package knr.yes24slc.dto;

import java.util.List;

public class OsrDashboardResponseDto {

    private String osrName;
    private int used;
    private int capacity;
    private int occupancyPct;
    private List<OsrOrderDto> orders;
    private List<OsrWaveDto> waves;

    public OsrDashboardResponseDto(String osrName,
                                   int used,
                                   int capacity,
                                   int occupancyPct,
                                   List<OsrOrderDto> orders,
                                   List<OsrWaveDto> waves) {
        this.osrName = osrName;
        this.used = used;
        this.capacity = capacity;
        this.occupancyPct = occupancyPct;
        this.orders = orders;
        this.waves = waves;
    }

    public String getOsrName() {
        return osrName;
    }

    public int getUsed() {
        return used;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupancyPct() {
        return occupancyPct;
    }

    public List<OsrOrderDto> getOrders() {
        return orders;
    }

    public List<OsrWaveDto> getWaves() {
        return waves;
    }
}