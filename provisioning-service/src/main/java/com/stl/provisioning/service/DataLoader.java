package com.stl.provisioning.service;

import com.stl.provisioning.entity.TelcoService;
import com.stl.provisioning.repository.TelcoServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner loadSampleData(TelcoServiceRepository telcoServiceRepository) {
        return args -> {
            // Sample services to be added when the application is loaded
            if (telcoServiceRepository.count() == 0) {  // To avoid duplicate entries
                // Adding International Roaming service
                TelcoService service1 = new TelcoService();
                service1.setName("International Roaming");
                service1.setDescription("Activate international roaming for seamless connectivity while traveling abroad.");
                service1.setPrice(500); // Example pricing, you can adjust as needed

                // Adding Ring-in Tone personalization service
                TelcoService service2 = new TelcoService();
                service2.setName("Ring-in Tone Personalisation");
                service2.setDescription("Personalize your ring-in tone with your favorite song or sound.");
                service2.setPrice(100);

                // Adding Data Top-up service
                TelcoService service3 = new TelcoService();
                service3.setName("Data Top-up");
                service3.setDescription("Get additional 5GB of data to stay connected.");
                service3.setPrice(250);

                // Adding another Value-Added Service (VAS) - Example: Call Forwarding
                TelcoService service4 = new TelcoService();
                service4.setName("Call Forwarding");
                service4.setDescription("Forward your incoming calls to another number while you're unavailable.");
                service4.setPrice(150);

                // Adding another Value-Added Service (VAS) - Example: SMS Bundle
                TelcoService service5 = new TelcoService();
                service5.setName("SMS Bundle");
                service5.setDescription("Activate a 500 SMS bundle for local messaging.");
                service5.setPrice(50);

                // Save services to the database
                telcoServiceRepository.save(service1);
                telcoServiceRepository.save(service2);
                telcoServiceRepository.save(service3);
                telcoServiceRepository.save(service4);
                telcoServiceRepository.save(service5);

                System.out.println("Sample Telco Services Loaded.");
            }
        };
    }
}
