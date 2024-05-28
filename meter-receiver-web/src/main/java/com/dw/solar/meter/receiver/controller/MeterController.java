package com.dw.solar.meter.receiver.controller;

import com.dw.solar.base.avro.SolarMeterInput;
import com.dw.solar.base.domain.SolarMeter;
import com.dw.solar.meter.receiver.producer.SendSolarMeterToKafka;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meter")
public class MeterController {

    private final SendSolarMeterToKafka sendSolarMeterToKafka;

    public MeterController(SendSolarMeterToKafka sendSolarMeterToKafka) {
        this.sendSolarMeterToKafka = sendSolarMeterToKafka;
    }

    @PostMapping("send")
    public void send(@RequestBody SolarMeter solarMeterRequest) {
        SolarMeterInput solarMeterInput = SolarMeterInput.newBuilder()
                .setId(solarMeterRequest.id())
                .setName(solarMeterRequest.name())
                .setUsage(solarMeterRequest.usage())
                .setMeterReadTs(solarMeterRequest.meterReadTs())
                .build();
        sendSolarMeterToKafka.sendMessage(solarMeterInput);
    }
}
