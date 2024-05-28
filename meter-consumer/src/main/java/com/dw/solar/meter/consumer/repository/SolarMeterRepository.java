package com.dw.solar.meter.consumer.repository;

import com.dw.solar.meter.consumer.entity.SolarMeter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SolarMeterRepository extends ReactiveCrudRepository<SolarMeter, Long> {
}
