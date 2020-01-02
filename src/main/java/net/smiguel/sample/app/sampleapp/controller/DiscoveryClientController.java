package net.smiguel.sample.app.sampleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.util.Optional;

@Controller
public class DiscoveryClientController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/service")
    public Optional<URI> serviceUrl() {
        return discoveryClient
                .getInstances(applicationName)
                .stream()
                .map(item -> item.getUri())
                .findFirst();
    }
}
