package com.dw.solar.meter.consumer.service;

import com.dw.solar.base.avro.SolarMeterInput;
import com.dw.solar.meter.consumer.entity.SolarMeter;
import com.dw.solar.meter.consumer.entity.SolarMeterUsage;
import com.dw.solar.meter.consumer.repository.SolarMeterRepository;
import com.dw.solar.meter.consumer.repository.SolarMeterUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class MeterConsumerService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final SolarMeterRepository solarMeterRepository;
    private final SolarMeterUsageRepository solarMeterUsageRepository;

    public MeterConsumerService(SolarMeterRepository solarMeterRepository,
                                SolarMeterUsageRepository solarMeterUsageRepository) {
        this.solarMeterRepository = solarMeterRepository;
        this.solarMeterUsageRepository = solarMeterUsageRepository;
    }

    @KafkaListener(topics = "#{'${solarmeter.message.topic}'}", groupId = "#{'${solarmeter.message.consumerGroup}'}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(SolarMeterInput solarMeterInput) {
        log.info("Consuming: {}", solarMeterInput);
        save(solarMeterInput);
    }

    private void save(SolarMeterInput solarMeterInput) {
        SolarMeter solarMeter;
        if (solarMeterInput.getId() == null) {
            solarMeter = new SolarMeter();
            solarMeter.setId(solarMeterInput.getId());
            solarMeter.setName(solarMeterInput.getName().toString());
            Mono<SolarMeter> solarMeterDb = solarMeterRepository.save(solarMeter);
            Optional<SolarMeter> solarMeterOptional = solarMeterDb.blockOptional();
            solarMeter = solarMeterOptional.get();
        } else {
            Mono<SolarMeter> solarMeterDb = solarMeterRepository.findById(solarMeterInput.getId());
            Optional<SolarMeter> solarMeterOptional = solarMeterDb.blockOptional();

            if (solarMeterOptional.isEmpty()) {
                solarMeter = new SolarMeter();
                solarMeter.setName(solarMeterInput.getName().toString());
                solarMeterRepository.save(solarMeter).subscribe();
            } else {
                solarMeter = solarMeterOptional.get();
            }
        }

        if (solarMeter != null) {
            SolarMeterUsage solarMeterUsage = new SolarMeterUsage();
            solarMeterUsage.setUsage(solarMeterInput.getUsage());
            LocalDateTime meterReadTs = LocalDateTime.parse(solarMeterInput.getMeterReadTs(), formatter);
            solarMeterUsage.setMeterReadTs(meterReadTs);
            solarMeterUsage.setSolarMeterId(solarMeter.getId());
            solarMeterUsageRepository.save(solarMeterUsage).subscribe();
        }
    }

}
