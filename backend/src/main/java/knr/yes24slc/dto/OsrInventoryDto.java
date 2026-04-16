package knr.yes24slc.dto;

import java.time.LocalDateTime;

public class OsrInventoryDto {

    private String id;                 // Location (예: W1b1111)
    private String repositoryId;       // Repository ID
    private String orderNo;            // Order 번호
    private String wave;               // Wave
    private String warehouseNo;        // 창고 번호
    private Integer isMoving;          // 이동 여부 (0 or 1)
    private LocalDateTime updateOperatedTime; // 업데이트 시간
    private String destination;        // 목적지

    public OsrInventoryDto() {
    }

    public OsrInventoryDto(String id,
                           String repositoryId,
                           String orderNo,
                           String wave,
                           String warehouseNo,
                           Integer isMoving,
                           LocalDateTime updateOperatedTime,
                           String destination) {
        this.id = id;
        this.repositoryId = repositoryId;
        this.orderNo = orderNo;
        this.wave = wave;
        this.warehouseNo = warehouseNo;
        this.isMoving = isMoving;
        this.updateOperatedTime = updateOperatedTime;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getWave() {
        return wave;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public Integer getIsMoving() {
        return isMoving;
    }

    public LocalDateTime getUpdateOperatedTime() {
        return updateOperatedTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public void setIsMoving(Integer isMoving) {
        this.isMoving = isMoving;
    }

    public void setUpdateOperatedTime(LocalDateTime updateOperatedTime) {
        this.updateOperatedTime = updateOperatedTime;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}