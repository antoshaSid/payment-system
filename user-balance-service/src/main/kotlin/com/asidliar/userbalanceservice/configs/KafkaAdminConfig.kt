package com.asidliar.userbalanceservice.configs

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaAdminConfig {

    @Bean
    fun kafkaAdmin(@Value("\${spring.kafka.bootstrap-servers}") bootstrapServer: String): KafkaAdmin {
        return KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServer))
    }

    @Bean
    fun adminClient(kafkaAdmin: KafkaAdmin): AdminClient {
        return AdminClient.create(kafkaAdmin.configurationProperties)
    }

    @Bean
    fun userBalanceUpdatesTopic(@Value("\${spring.kafka.topic.name}") topicName: String): NewTopic {
        return NewTopic(topicName, 3, 1.toShort())
    }
}