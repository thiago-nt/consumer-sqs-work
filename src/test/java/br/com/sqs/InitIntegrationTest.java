package br.com.sqs;

import br.com.sqs.consumer.ConsumerSQS;
import br.com.sqs.util.ContextUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class InitIntegrationTest {

    private Context testContext = ContextUtil.createContext();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void initIntegration() throws JsonProcessingException {
        ConsumerSQS consumerSQS = new ConsumerSQS();
        SQSEvent sqsEvent = new SQSEvent();
        sqsEvent.setRecords(new ArrayList<>());

        for(int count = 0; count < 20; count++){
            sqsEvent.getRecords().add(buildSqsMessage(count));
        }
        consumerSQS.handleRequest(sqsEvent, testContext);
    }

    private SQSEvent.SQSMessage buildSqsMessage(Integer sistemaOrigem) throws JsonProcessingException{
        SQSEvent.SQSMessage sqsMessage = new SQSEvent.SQSMessage();
        sqsMessage.setBody(this.objectMapper.writeValueAsString("Writer SQS"));
        return sqsMessage;
    }
}
