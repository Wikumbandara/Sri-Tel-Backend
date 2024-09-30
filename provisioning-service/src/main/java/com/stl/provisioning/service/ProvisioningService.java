package com.stl.provisioning.service;

import com.stl.provisioning.entity.Provisioning;
import com.stl.provisioning.entity.TelcoService;
import com.stl.user.entity.User;
import com.stl.provisioning.repository.ProvisioningRepository;
import com.stl.provisioning.repository.TelcoServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProvisioningService {

    @Autowired
    private ProvisioningRepository repository;

    @Autowired
    private TelcoServiceRepository serviceRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8081/api/users/id/";

    public Provisioning activateService(Long serviceId, Long userId) {
        // Call UserService to validate user
        String userUrl = USER_SERVICE_URL + userId;
        User user = restTemplate.getForObject(userUrl, User.class);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Provisioning provisioning = new Provisioning();
        TelcoService service = serviceRepository.findById(serviceId).
                orElseThrow(() -> new RuntimeException("Service not found"));
        provisioning.setService(service);
        provisioning.setUserId(userId);
        provisioning.setActive(true);
        return repository.save(provisioning);
    }

    public Provisioning deactivateService(Long serviceId, Long userId) {
        Provisioning provisioning = (Provisioning) repository.findByServiceIdAndUserId(serviceId, userId).
                orElseThrow(() -> new RuntimeException("Provisioning not found"));
        provisioning.setActive(false);
        return repository.save(provisioning);
    }

    public Iterable<Optional<Object>> getProvisioningByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Iterable<TelcoService> getAllTelcoServices() {
        return serviceRepository.findAll();
    }
}
