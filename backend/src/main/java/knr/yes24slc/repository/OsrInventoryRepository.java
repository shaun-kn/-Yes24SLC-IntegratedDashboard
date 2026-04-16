package knr.yes24slc.repository;

import knr.yes24slc.dto.OsrInventoryDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OsrInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public OsrInventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OsrInventoryDto> findAll() {

        String sql =
                "SELECT " +
                        "Id, " +
                        "RepositoryId, " +
                        "OrderNo, " +
                        "Wave, " +
                        "WarehouseNo, " +
                        "IsMoving, " +
                        "UpdateOperatedTime, " +
                        "Destination " +
                        "FROM OsrInventory";

        return jdbcTemplate.query(sql, this::mapRow);
    }

    private OsrInventoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        OsrInventoryDto dto = new OsrInventoryDto();

        dto.setId(rs.getString("Id"));
        dto.setRepositoryId(rs.getString("RepositoryId"));
        dto.setOrderNo(rs.getString("OrderNo"));
        dto.setWave(rs.getString("Wave"));
        dto.setWarehouseNo(rs.getString("WarehouseNo"));

        // null-safe 처리 (추천)
        Object isMovingObj = rs.getObject("IsMoving");
        if (isMovingObj != null) {
            dto.setIsMoving(rs.getInt("IsMoving"));
        }

        if (rs.getTimestamp("UpdateOperatedTime") != null) {
            dto.setUpdateOperatedTime(
                    rs.getTimestamp("UpdateOperatedTime").toLocalDateTime()
            );
        }

        dto.setDestination(rs.getString("Destination"));

        return dto;
    }
}