package knr.yes24slc.service;

import knr.yes24slc.dto.OsrDashboardResponseDto;
import knr.yes24slc.dto.OsrInventoryDto;
import knr.yes24slc.dto.OsrOrderDto;
import knr.yes24slc.dto.OsrWaveDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OsrDashboardService {

    private final OsrInventoryCache cache;

    public OsrDashboardService(OsrInventoryCache cache) {
        this.cache = cache;
    }

    public OsrDashboardResponseDto getDashboard(int osrId) {
        List<OsrInventoryDto> all = cache.get();

        switch (osrId) {
            case 2:
                return buildOsr2(all);
            case 3:
                return buildOsr3(all);
            default:
                throw new IllegalArgumentException("Unsupported osrId: " + osrId);
        }
    }

    private OsrDashboardResponseDto buildOsr2(List<OsrInventoryDto> all) {
        List<OsrInventoryDto> rows = all.stream()
                .filter(this::isOsr2)
                .collect(Collectors.toList());

        return buildOrderResponse("OSR2", 2, rows);
    }

    private OsrDashboardResponseDto buildOsr3(List<OsrInventoryDto> all) {
        List<OsrInventoryDto> rows = all.stream()
                .filter(this::isOsr3)
                .collect(Collectors.toList());

        int capacity = getCapacityByOsrId(3);

        int used = (int) rows.stream()
                .filter(this::isUsed)
                .count();

        int occupancyPct = capacity == 0
                ? 0
                : (int) Math.round((used * 100.0) / capacity);

        Map<String, List<OsrInventoryDto>> grouped = rows.stream()
                .filter(x -> hasText(x.getWave()))
                .collect(Collectors.groupingBy(OsrInventoryDto::getWave));

        List<WaveSummary> summaries = grouped.entrySet().stream()
                .map(entry -> {
                    String wave = entry.getKey();
                    List<OsrInventoryDto> waveRows = entry.getValue();

                    int toteCount = waveRows.size();

                    LocalDateTime maxUpdateTime = waveRows.stream()
                            .map(OsrInventoryDto::getUpdateOperatedTime)
                            .filter(t -> t != null)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);

                    return new WaveSummary(wave, toteCount, maxUpdateTime);
                })
                .sorted((a, b) -> {
                    int countCompare = Integer.compare(b.getToteCount(), a.getToteCount());
                    if (countCompare != 0) {
                        return countCompare;
                    }

                    if (a.getMaxUpdateTime() == null && b.getMaxUpdateTime() == null) {
                        return 0;
                    }
                    if (a.getMaxUpdateTime() == null) {
                        return 1;
                    }
                    if (b.getMaxUpdateTime() == null) {
                        return -1;
                    }

                    return b.getMaxUpdateTime().compareTo(a.getMaxUpdateTime());
                })
                .limit(6)
                .collect(Collectors.toList());

        List<OsrWaveDto> waves = summaries.stream()
                .map(x -> new OsrWaveDto(x.getWave(), x.getToteCount()))
                .collect(Collectors.toList());

        return new OsrDashboardResponseDto(
                "OSR3",
                used,
                capacity,
                occupancyPct,
                null,
                waves
        );
    }

    private OsrDashboardResponseDto buildOrderResponse(String osrName, int osrId, List<OsrInventoryDto> rows) {
        int capacity = getCapacityByOsrId(osrId);

        int used = (int) rows.stream()
                .filter(this::isUsed)
                .count();

        int occupancyPct = capacity == 0
                ? 0
                : (int) Math.round((used * 100.0) / capacity);

        Map<String, List<OsrInventoryDto>> grouped = rows.stream()
                .filter(x -> hasText(x.getOrderNo()))
                .collect(Collectors.groupingBy(OsrInventoryDto::getOrderNo));

        List<OsrOrderDto> orders = grouped.entrySet().stream()
                .sorted(Comparator.comparing(entry ->
                        entry.getValue().stream()
                                .map(OsrInventoryDto::getUpdateOperatedTime)
                                .filter(Objects::nonNull)
                                .min(Comparator.naturalOrder())
                                .orElse(LocalDateTime.MAX)
                ))
                .map(entry -> {
                    String orderNo = entry.getKey();
                    List<OsrInventoryDto> orderRows = entry.getValue();

                    int totalCount = orderRows.size();

                    int enteredCount = (int) orderRows.stream()
                            .filter(x -> x.getIsMoving() != null && x.getIsMoving() == 0)
                            .count();

                    String status = orderRows.stream()
                            .anyMatch(x -> x.getIsMoving() != null && x.getIsMoving() == 1)
                            ? "이동중"
                            : "";

                    LocalDateTime updateTime = orderRows.stream()
                            .map(OsrInventoryDto::getUpdateOperatedTime)
                            .filter(Objects::nonNull)
                            .min(Comparator.naturalOrder())
                            .orElse(null);

                    return new OsrOrderDto(orderNo, totalCount, enteredCount, status, updateTime);
                })
                .limit(20)
                .collect(Collectors.toList());

        return new OsrDashboardResponseDto(
                osrName,
                used,
                capacity,
                occupancyPct,
                orders,
                null
        );
    }

    private OsrDashboardResponseDto buildOrderResponse11(String osrName, int osrId, List<OsrInventoryDto> rows) {
        int capacity = getCapacityByOsrId(osrId);

        int used = (int) rows.stream()
                .filter(this::isUsed)
                .count();

        int occupancyPct = capacity == 0
                ? 0
                : (int) Math.round((used * 100.0) / capacity);

        Map<String, List<OsrInventoryDto>> grouped = rows.stream()
                .filter(x -> hasText(x.getOrderNo()))
                .collect(Collectors.groupingBy(OsrInventoryDto::getOrderNo));

        List<OsrOrderDto> orders = grouped.entrySet().stream()
                .sorted(Comparator.comparing(entry ->
                        entry.getValue().stream()
                                .map(OsrInventoryDto::getUpdateOperatedTime)
                                .filter(Objects::nonNull)
                                .min(Comparator.naturalOrder())
                                .orElse(LocalDateTime.MAX)
                ))
                .map(entry -> {
                    String orderNo = entry.getKey();
                    List<OsrInventoryDto> orderRows = entry.getValue();

                    int totalCount = orderRows.size();
                    int enteredCount = (int) orderRows.stream()
                            .filter(x -> x.getIsMoving() != null && x.getIsMoving() == 0)
                            .count();


                    String status = orderRows.stream()
                            .anyMatch(x -> x.getIsMoving() != null && x.getIsMoving() == 1)
                            ? "배출중"
                            : "";

                    LocalDateTime updateTime = orderRows.stream()
                            .map(OsrInventoryDto::getUpdateOperatedTime)
                            .filter(Objects::nonNull)
                            .min(Comparator.naturalOrder())
                            .orElse(null);

                    return new OsrOrderDto(orderNo, totalCount, enteredCount, status, updateTime);
                    //return new OsrOrderDto(orderNo, totalCount, enteredCount, status);
                })
                .limit(20)
                .collect(Collectors.toList());

        return new OsrDashboardResponseDto(
                osrName,
                used,
                capacity,
                occupancyPct,
                orders,
                null
        );
    }

//    private OsrDashboardResponseDto buildOrderResponse2(String osrName, int osrId, List<OsrInventoryDto> rows) {
//        int capacity = getCapacityByOsrId(osrId);
//
//        int used = (int) rows.stream()
//                .filter(this::isUsed)
//                .count();
//
//        int occupancyPct = capacity == 0
//                ? 0
//                : (int) Math.round((used * 100.0) / capacity);
//
//        Map<String, List<OsrInventoryDto>> grouped = rows.stream()
//                .filter(x -> hasText(x.getOrderNo()))
//                .collect(Collectors.groupingBy(OsrInventoryDto::getOrderNo));
//
//        List<OsrOrderDto> orders = grouped.entrySet().stream()
//                .sorted(Comparator.comparing(entry ->
//                        entry.getValue().stream()
//                                .map(OsrInventoryDto::getUpdateOperatedTime)
//                                .filter(Objects::nonNull)
//                                .min(Comparator.naturalOrder())
//                                .orElse(LocalDateTime.MAX)
//                ))
//                .map(entry -> {
//                    String orderNo = entry.getKey();
//                    List<OsrInventoryDto> orderRows = entry.getValue();
//
//                    int totalCount = orderRows.size();
//                    int enteredCount = (int) orderRows.stream()
//                            .filter(x -> x.getIsMoving() != null && x.getIsMoving() == 0)
//                            .count();
//
//                    return new OsrOrderDto(orderNo, totalCount, enteredCount);
//                })
//                .limit(20)
//                .collect(Collectors.toList());
//
//        return new OsrDashboardResponseDto(
//                osrName,
//                used,
//                capacity,
//                occupancyPct,
//                orders,
//                null
//        );
//    }

//    private OsrDashboardResponseDto buildOrderResponse1(String osrName, int osrId, List<OsrInventoryDto> rows) {
//        int capacity = getCapacityByOsrId(osrId);
//
//        int used = (int) rows.stream()
//                .filter(this::isUsed)
//                .count();
//
//        int occupancyPct = capacity == 0
//                ? 0
//                : (int) Math.round((used * 100.0) / capacity);
//
//        Map<String, List<OsrInventoryDto>> grouped = rows.stream()
//                .filter(x -> hasText(x.getOrderNo()))
//                .collect(Collectors.groupingBy(OsrInventoryDto::getOrderNo));
//
//        List<OsrOrderDto> orders = grouped.entrySet().stream()
//                .map(entry -> {
//                    String orderNo = entry.getKey();
//                    List<OsrInventoryDto> orderRows = entry.getValue();
//
//                    int totalCount = orderRows.size();
//
//                    int enteredCount = (int) orderRows.stream()
//                            .filter(x -> x.getIsMoving() != null && x.getIsMoving() == 0)
//                            .count();
//
//                    // ⭐ 가장 오래된 시간 (핵심)
//                    LocalDateTime firstUpdateTime = orderRows.stream()
//                            .map(OsrInventoryDto::getUpdateOperatedTime)
//                            .filter(Objects::nonNull)
//                            .min(LocalDateTime::compareTo)
//                            .orElse(LocalDateTime.MAX);
//
//                    return new OsrOrderDto(orderNo, totalCount, enteredCount, firstUpdateTime);
//                })
//                // ⭐ 오래된 순 정렬
//                .sorted(Comparator.comparing(OsrOrderDto::getFirstUpdateTime))
//                .limit(20)
//                .collect(Collectors.toList());
//
//        return new OsrDashboardResponseDto(
//                osrName,
//                used,
//                capacity,
//                occupancyPct,
//                orders,
//                null
//        );
//    }

    private int getCapacityByOsrId(int osrId) {
        switch (osrId) {
            case 2:
                return 1440; // W02
            case 3:
                return 560;  // W03
            default:
                return 0;
        }
    }

    private boolean isUsed(OsrInventoryDto dto) {
        return hasText(dto.getOrderNo());
    }

    private boolean isOsr2(OsrInventoryDto dto) {
        return "W02".equals(trim(dto.getWarehouseNo()));
    }

    private boolean isOsr3(OsrInventoryDto dto) {
        return "W03".equals(trim(dto.getWarehouseNo()));
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private static class WaveSummary {
        private final String wave;
        private final int toteCount;
        private final LocalDateTime maxUpdateTime;

        public WaveSummary(String wave, int toteCount, LocalDateTime maxUpdateTime) {
            this.wave = wave;
            this.toteCount = toteCount;
            this.maxUpdateTime = maxUpdateTime;
        }

        public String getWave() {
            return wave;
        }

        public int getToteCount() {
            return toteCount;
        }

        public LocalDateTime getMaxUpdateTime() {
            return maxUpdateTime;
        }
    }
}