package com.dw.solar.meter.receiver.producer;

import com.dw.solar.base.avro.SolarMeterInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendSolarMeterToKafka {

    @Value("${solarmeter.message.topic}")
    private String topic;

    private final KafkaTemplate<String, SolarMeterInput> kafkaTemplate;

    public SendSolarMeterToKafka(KafkaTemplate<String, SolarMeterInput> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(SolarMeterInput solarMeterInput) {
        log.info("Sending message to topic {} -> {}", topic, solarMeterInput.toString());
        this.kafkaTemplate.send(topic, solarMeterInput);
    }
}
