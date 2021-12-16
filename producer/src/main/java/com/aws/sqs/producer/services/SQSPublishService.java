package com.aws.sqs.producer.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SQSPublishService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSPublishService.class);
    public static final String SAMPLE_QUEUE_FIFO = "http://localhost:4566/000000000000/sample-queue.fifo";
    public static final String SAMPLE_MESSAGE = "Sample Message";
    public static final String PUBLISHED_IN_SQS = "Event has been published in SQS.";

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishEvent(JsonNode message) {
        LOGGER.info("Generating event : {}", message);
        SendMessageRequest sendMessageRequest = null;
        try {
            sendMessageRequest = new SendMessageRequest().withQueueUrl(SAMPLE_QUEUE_FIFO)
                    .withMessageBody(objectMapper.writeValueAsString(message))
                    .withMessageGroupId(SAMPLE_MESSAGE)
                    .withMessageDeduplicationId(UUID.randomUUID().toString());
            amazonSQS.sendMessage(sendMessageRequest);
            LOGGER.info(PUBLISHED_IN_SQS);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }

    }
}
