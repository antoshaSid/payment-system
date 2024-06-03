package com.asidliar.userbalanceservice.services.producers

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity

/**
 * Producer service that sends user balance updates to a Kafka topic.
 */
interface UserBalanceUpdateProducer {

    /**
     * Sends a batch of user balance updates to the Kafka topic.
     *
     * @param userBalanceUpdateBatch The batch of user balance updates to send.
     */
    fun sendUserBalanceUpdateBatch(userBalanceUpdateBatch: List<Map.Entry<Int, Int>>): ExecutionJobEntity
}