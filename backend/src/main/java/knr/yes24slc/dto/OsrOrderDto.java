package knr.yes24slc.dto;

import java.time.LocalDateTime;

public class OsrOrderDto {

    private String orderNo;
    private int totalCount;
    private int enteredCount;
    private String status;
    private LocalDateTime updateTime;

    public OsrOrderDto(String orderNo, int totalCount, int enteredCount, String status, LocalDateTime updateTime) {
        this.orderNo = orderNo;
        this.totalCount = totalCount;
        this.enteredCount = enteredCount;
        this.status = status;
        this.updateTime = updateTime;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getEnteredCount() {
        return enteredCount;
    }
    public String getStatus() {
        return status;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

}