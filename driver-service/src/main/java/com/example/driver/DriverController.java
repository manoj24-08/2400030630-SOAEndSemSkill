package com.example.driver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Value("${server.instance:Driver-Instance}")
    private String instance;

    @GetMapping
    public Map<String, String> getDrivers() {
        return Map.of(
            "service", "Driver Service",
            "instance", instance,
            "message", "Request handled successfully"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "instance", instance);
    }
}
