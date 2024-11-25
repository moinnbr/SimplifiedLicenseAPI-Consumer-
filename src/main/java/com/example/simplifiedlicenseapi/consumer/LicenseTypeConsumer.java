package com.example.simplifiedlicenseapi.consumer;

import com.example.simplifiedlicenseapi.model.LicenseType;
import com.example.simplifiedlicenseapi.service.LicenseTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LicenseTypeConsumer {
    @Autowired
    private LicenseTypeService licenseTypeService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "license-topic", groupId =
            "license-type-group")
    public void consume(String message) {
        try {
            String[] messageFields = message.split(",");
            LicenseType licenseType = new LicenseType();
            licenseType.setLicenseTypeCode(messageFields[0]);
            licenseType.setMinistryCode(messageFields[1]);
            licenseType.setCanBeUsedPartially(Boolean.parseBoolean(messageFields[2]));
            licenseTypeService.saveLicenseType(licenseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}