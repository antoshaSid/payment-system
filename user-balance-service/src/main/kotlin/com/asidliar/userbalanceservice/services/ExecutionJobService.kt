package com.asidliar.userbalanceservice.services

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import com.asidliar.userbalanceservice.models.ExecutionJobStatus

/**
 * Service for managing execution jobs required to handle asynchronous operations.
 */
interface ExecutionJobService {

    /**
     * Creates a new execution job.
     *
     * @return The newly created execution job.
     */
    fun createExecutionJob(): ExecutionJobEntity

    /**
     * Retrieves an execution job.
     *
     * @param id The ID of the execution job.
     * @return The execution job.
     */
    fun getExecutionJob(id: Int): ExecutionJobEntity?

    /**
     * Retrieves the status of an execution job.
     *
     * @param id The ID of the execution job.
     * @return The status of the execution job.
     */
    fun getExecutionJobStatus(id: Int): ExecutionJobStatus?

    /**
     * Updates the status of an execution job.
     *
     * @param id The ID of the execution job.
     * @param status The new status of the execution job.
     */
    fun updateExecutionJobStatus(id: Int, status: ExecutionJobStatus): Boolean

    /**
     * Waits for an execution job to complete.
     *
     * @param executionJobEntity The execution job to wait for.
     */
    fun waitForExecutionJobToComplete(executionJobEntity: ExecutionJobEntity)

    /**
     * Waits for an execution job batch to complete.
     *
     * @param executionJobBatch The execution job batch to wait for.
     */
    fun waitForExecutionJobBatchToComplete(executionJobBatch: ExecutionJobBatch)
}