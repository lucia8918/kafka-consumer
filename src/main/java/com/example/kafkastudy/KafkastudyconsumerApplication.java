package com.example.kafkastudy;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.SimpleKafkaHeaderMapper;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

@SpringBootApplication
public class KafkastudyconsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkastudyconsumerApplication.class, args);
  }
}
