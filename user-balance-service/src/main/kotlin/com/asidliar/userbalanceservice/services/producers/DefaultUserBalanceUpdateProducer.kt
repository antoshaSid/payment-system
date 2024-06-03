package com.asidliar.userbalanceservice.services.producers

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import com.asidliar.userbalanceservice.models.ExecutionJobStatus
import com.asidliar.userbalanceservice.services.ExecutionJobService
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class DefaultUserBalanceUpdateProducer(private val kafkaTemplate: KafkaTemplate<Int, List<Map.Entry<Int, Int>>>,
                                       private val executionJobService: ExecutionJobService): UserBalanceUpdateProducer {

    @Value("\${spring.kafka.topic.name}")
    private lateinit var topicName: String

    override fun sendUserBalanceUpdateBatch(userBalanceUpdateBatch: List<Map.Entry<Int, Int>>): ExecutionJobEntity {
        val executionJob: ExecutionJobEntity = executionJobService.createExecutionJob()
        val executionJobId: Int = executionJob.id!!

        kafkaTemplate.send(topicName, executionJobId, userBalanceUpdateBatch)
        executionJobService.updateExecutionJobStatus(executionJobId, ExecutionJobStatus.IN_QUEUE)

        return executionJob
    }
}
