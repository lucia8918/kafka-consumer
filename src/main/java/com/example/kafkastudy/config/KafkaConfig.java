package com.example.kafkastudy.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

  /**
   * ReplyingKafkaTemplate 관련 Bean 설정 들
   * https://shuaibabdulla40.medium.com/apache-kafka-request-reply-semantics-implementation-replyingkafkatemplate-5bf64958268c
   *
   */
  // @Bean(name = "ReplyingKafkaTemplate")
  public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
      ProducerFactory<String, String> pf,
      KafkaMessageListenerContainer<String, String> replyContainer) {
    return new ReplyingKafkaTemplate<>(pf, replyContainer);
  }

  // @Bean
  public KafkaMessageListenerContainer<String, String> replyContainer(
      ConsumerFactory<String, String> cf) {
    ContainerProperties containerProperties = new ContainerProperties(
        "kReplies");
    return new KafkaMessageListenerContainer<>(cf, containerProperties);
  }

  // @Bean
  public NewTopic kRequests() {
    return new NewTopic("kRequests", 1, (short) 1);
  }

  // @Bean
  public NewTopic kReplies() {
    return new NewTopic("kReplies", 1, (short) 1);
  }

}
