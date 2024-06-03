package com.asidliar.userbalanceservice.services.consumers

import com.asidliar.userbalanceservice.models.ExecutionJobStatus
import com.asidliar.userbalanceservice.repositories.UserBalanceRepository
import com.asidliar.userbalanceservice.services.ExecutionJobService
import jakarta.transaction.Transactional
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
@Transactional
class DefaultUserBalanceUpdateConsumer(private val userBalanceRepository: UserBalanceRepository,
                                       private val executionJobService: ExecutionJobService): UserBalanceUpdateConsumer {

    private val log: Logger = LoggerFactory.getLogger(DefaultUserBalanceUpdateConsumer::class.java)

    @KafkaListener(topics = ["#{'\${spring.kafka.topic.name}'}"], groupId = "#{'\${spring.kafka.consumer.group-id}'}")
    override fun consumeUserBalanceUpdateBatch(record: ConsumerRecord<Int, List<Map.Entry<Int, Int>>>,
                                               acknowledgment: Acknowledgment) {
        val executionJobId: Int = record.key()
        try {
            executionJobService.updateExecutionJobStatus(executionJobId, ExecutionJobStatus.IN_PROGRESS)

            val userBalanceUpdateBatch: List<Map.Entry<Int, Int>> = record.value()
            val userBalanceMap = ConcurrentHashMap(userBalanceUpdateBatch.associate { it.key to it.value })

            val updatedUserBalances = userBalanceRepository.findAllById(userBalanceMap.keys).parallelStream()
                .peek { it.balance = userBalanceMap[it.id]!! }
                .toList()
            userBalanceRepository.saveAll(updatedUserBalances)

            log.info("Processed user balance update batch")
            acknowledgment.acknowledge()
            executionJobService.updateExecutionJobStatus(executionJobId, ExecutionJobStatus.COMPLETED)
        } catch (e: Exception) {
            log.error("Failed to process user balance update batch", e)
            executionJobService.updateExecutionJobStatus(executionJobId, ExecutionJobStatus.FAILED)
            throw e
        }
    }
}