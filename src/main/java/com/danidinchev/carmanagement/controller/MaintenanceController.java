package com.danidinchev.carmanagement.controller;

import com.danidinchev.carmanagement.dto.CreateMaintenanceDTO;
import com.danidinchev.carmanagement.dto.MonthlyRequestsReportDTO;
import com.danidinchev.carmanagement.dto.ResponseMaintenanceDTO;
import com.danidinchev.carmanagement.dto.UpdateMaintenanceDTO;
import com.danidinchev.carmanagement.entity.Maintenance;
import com.danidinchev.carmanagement.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/maintenance")
    public class MaintenanceController {

        private final MaintenanceService maintenanceService;

        @Autowired
        public MaintenanceController(MaintenanceService maintenanceService) {
            this.maintenanceService = maintenanceService;
        }

        @PostMapping
        public Maintenance addMaintenance(@RequestBody CreateMaintenanceDTO createMaintenanceDTO){

            return maintenanceService.createMaintenance(createMaintenanceDTO);

        }

        @GetMapping("/{id}")
        public List<ResponseMaintenanceDTO> getMaintenanceById(@PathVariable Long id){
            return maintenanceService.findById(id);
        }

        @PutMapping("/{id}")
        public Maintenance updateMaintenance(@PathVariable Long id, @RequestBody UpdateMaintenanceDTO updateMaintenanceDTO){

            return maintenanceService.updateMaintenance(id, updateMaintenanceDTO);
        }

        @DeleteMapping("/{id}")
        public void deleteMaintenance(@PathVariable Long id){
            maintenanceService.deleteById(id);
        }

        @GetMapping
        public List<ResponseMaintenanceDTO> getAllMaintenances(
                @RequestParam(required = false) Long garageId,
                @RequestParam(required = false) Long carId
        ) {
            return maintenanceService.findMaintenances(garageId, carId);
        }

        @GetMapping("/monthlyRequestsReport")
        public ResponseEntity<List<MonthlyRequestsReportDTO>> monthlyRequestsReport(@RequestParam Long garageId,
                                                                                    @RequestParam String startMonth,
                                                                                    @RequestParam String endMonth) {
            List<MonthlyRequestsReportDTO> monthlyRequestsReportDTO =
                    maintenanceService.getMonthlyRequestReport(garageId, startMonth, endMonth);
            return ResponseEntity.ok(monthlyRequestsReportDTO);
        }
}
