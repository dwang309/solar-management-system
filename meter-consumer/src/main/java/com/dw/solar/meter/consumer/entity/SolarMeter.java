package com.dw.solar.meter.consumer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SolarMeter {
    @Id
    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdTs;
    private String updatedBy;
    private LocalDateTime updatedTs;
}
