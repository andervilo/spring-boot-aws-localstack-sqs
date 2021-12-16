package com.aws.sqs.producer.rest;

import com.aws.sqs.producer.services.SQSPublishService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {

    @Autowired
    private SQSPublishService sqsPublishService;

    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(@RequestBody JsonNode jsonNode) {
        sqsPublishService.publishEvent(jsonNode);
        return ResponseEntity.ok().build();
    }
}
