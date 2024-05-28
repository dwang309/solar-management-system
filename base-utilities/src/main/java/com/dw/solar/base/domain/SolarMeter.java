package com.dw.solar.base.domain;

public record SolarMeter(Long id, String name, double usage, String meterReadTs) {
}
