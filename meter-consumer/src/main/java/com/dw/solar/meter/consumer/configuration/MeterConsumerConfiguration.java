package com.dw.solar.meter.consumer.configuration;

import com.dw.solar.base.avro.SolarMeterInput;
import com.dw.solar.base.utils.AvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MeterConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${solarmeter.message.consumerGroup}")
    private String consumerGroup;

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        return properties;
    }

    @Bean
    public ConsumerFactory<String, SolarMeterInput> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(),
                new StringDeserializer(),
                new AvroDeserializer<>(SolarMeterInput.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SolarMeterInput> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SolarMeterInput> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
