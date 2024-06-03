package com.asidliar.userbalanceservice.services

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import com.asidliar.userbalanceservice.models.ExecutionJobStatus
import com.asidliar.userbalanceservice.repositories.ExecutionJobRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


@Service
@Transactional
class ExecutionJobServiceImpl(private val executionJobRepository: ExecutionJobRepository): ExecutionJobService {

    override fun createExecutionJob(): ExecutionJobEntity {
        return executionJobRepository.save(ExecutionJobEntity())
    }

    override fun getExecutionJob(id: Int): ExecutionJobEntity? {
        val jobOptional: Optional<ExecutionJobEntity> = executionJobRepository.findById(id)

        return if (jobOptional.isPresent) {
            jobOptional.get()
        } else {
            null
        }
    }

    override fun getExecutionJobStatus(id: Int): ExecutionJobStatus? {
        getExecutionJob(id)?.let {
            return it.status
        }

        return null
    }

    override fun updateExecutionJobStatus(id: Int, status: ExecutionJobStatus): Boolean {
        getExecutionJob(id)?.let {
            executionJobRepository.save(ExecutionJobEntity(id, status))
            return true
        }

        return false
    }

    override fun waitForExecutionJobToComplete(executionJobEntity: ExecutionJobEntity) {
        var status: ExecutionJobStatus?
        do {
            status = getExecutionJobStatus(executionJobEntity.id!!)
            TimeUnit.SECONDS.sleep(1)
        } while (status == ExecutionJobStatus.IN_QUEUE || status == ExecutionJobStatus.IN_PROGRESS)
    }

    override fun waitForExecutionJobBatchToComplete(executionJobBatch: ExecutionJobBatch) {
        val futures: List<CompletableFuture<Void>> = executionJobBatch.executionJobBatch.parallelStream()
            .map { job ->
                CompletableFuture.runAsync {
                    try {
                        waitForExecutionJobToComplete(job)
                    } catch (e: InterruptedException) {
                        throw RuntimeException(e)
                    }
                }
            }
            .toList()

        val allOf = CompletableFuture.allOf(*futures.toTypedArray())
        allOf.get()
    }
}