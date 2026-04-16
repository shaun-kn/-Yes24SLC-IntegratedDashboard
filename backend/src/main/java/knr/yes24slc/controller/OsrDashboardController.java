package knr.yes24slc.controller;

import knr.yes24slc.dto.OsrDashboardResponseDto;
import knr.yes24slc.service.OsrDashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/osr")
@CrossOrigin
public class OsrDashboardController {

    private final OsrDashboardService service;

    public OsrDashboardController(OsrDashboardService service) {
        this.service = service;
    }

    @GetMapping("/{osrId}")
    public OsrDashboardResponseDto getDashboard(@PathVariable int osrId) {
        return service.getDashboard(osrId);
    }
}