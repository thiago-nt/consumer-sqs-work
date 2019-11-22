package br.com.sqs.consumer;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ConsumerSQS implements RequestHandler<SQSEvent, Void> {

    private final static String URL = "";

    private final AWSLambda lambda = AWSLambdaClientBuilder.standard().build();

    private final String functionInvokeRequest = "consumerWorker-" + System.getenv("env") + "-exampleConsumer";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSMessage msg : event.getRecords()) {
            log.info("Consumer -> body {} ", msg.getBody());
            try {
                sendServiceRest(msg.getBody().toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Consumer 1 -> {} ", e);
            }
        }
        return null;
    }

    public void sendServiceRest(String json) {
        log.info("sendServiceRest -> json {} ", json);
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = URL;

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        HttpEntity<String> request =
                new HttpEntity<String>(json, headers);

        ResponseEntity<String> result = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        log.info("getStatusCode -> json {} ", result.getStatusCode());
    }
}