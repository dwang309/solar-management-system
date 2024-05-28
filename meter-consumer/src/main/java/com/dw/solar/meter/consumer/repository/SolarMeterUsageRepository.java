package com.dw.solar.meter.consumer.repository;

import com.dw.solar.meter.consumer.entity.SolarMeterUsage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SolarMeterUsageRepository extends ReactiveCrudRepository<SolarMeterUsage, Long> {
}
