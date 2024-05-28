package com.dw.solar.meter.consumer.entity;

import com.dw.solar.base.domain.SolarMeter;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SolarMeterUsage {
    @Id
    private Long id;

    private Long solarMeterId;
    @Transient
    private SolarMeter solarMeter;

    private double usage;
    private LocalDateTime meterReadTs;
    private String createdBy;
    private LocalDateTime createdTs;
    private String updatedBy;
    private LocalDateTime updatedTs;
}
