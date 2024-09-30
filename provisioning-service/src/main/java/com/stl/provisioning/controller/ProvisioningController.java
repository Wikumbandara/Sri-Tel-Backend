package com.stl.provisioning.controller;


import com.stl.provisioning.dto.ProvisioningDTO;
import com.stl.provisioning.entity.Provisioning;
import com.stl.provisioning.entity.TelcoService;
import com.stl.provisioning.service.ProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/provision")
public class ProvisioningController {
    @Autowired
    private ProvisioningService userService;

    @PostMapping("/activate")
    public ResponseEntity<Provisioning> provisionService(@RequestBody ProvisioningDTO provisioningDTO) {
        Provisioning service = userService.activateService(provisioningDTO.getServiceId(), provisioningDTO.getUserId());
        return ResponseEntity.ok(service);
    }

    @PostMapping("/deactivate")
    public ResponseEntity<Provisioning> deprovisionService(@RequestBody ProvisioningDTO provisioningDTO) {
        Provisioning service = userService.deactivateService(provisioningDTO.getServiceId(), provisioningDTO.getUserId());
        return ResponseEntity.ok(service);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Optional<Object>>> getProvisioningByUserId(@PathVariable Long userId) {
        Iterable<Optional<Object>> provisioning = userService.getProvisioningByUserId(userId);
        return ResponseEntity.ok(provisioning);
    }

    @GetMapping("/services")
    public ResponseEntity<Iterable<TelcoService>> getAllTelcoServices() {
        Iterable<TelcoService> services = userService.getAllTelcoServices();
        return ResponseEntity.ok(services);
    }
}
