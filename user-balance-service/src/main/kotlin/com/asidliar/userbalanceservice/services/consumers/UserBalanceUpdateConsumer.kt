package com.asidliar.userbalanceservice.services.consumers

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.support.Acknowledgment

/**
 * Consumer service that listens for user balance updates from a Kafka topic.
 */
interface UserBalanceUpdateConsumer {

    /**
     * Consumes a batch of user balance updates from the Kafka topic.
     *
     * @param record The Kafka record containing the user balance updates
     * @param acknowledgment The acknowledgment object to use to acknowledge the record
     */
    fun consumeUserBalanceUpdateBatch(record: ConsumerRecord<Int, List<Map.Entry<Int, Int>>>,
                                      acknowledgment: Acknowledgment
    )
}