package knr.yes24slc.integration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotInventoryDto {

    private int rackId;
    private int row;
    private int col;
    private String slotId;

    private int status;        // enum code
    private String itemCode;
    private int quantity;

    private String updatedAt;
}