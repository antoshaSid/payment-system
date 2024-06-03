package com.asidliar.userbalanceservice.rest

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import com.asidliar.userbalanceservice.services.ExecutionJobService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/execution/jobs", produces = ["application/json"])
class ExecutionJobResource(private val executionJobService: ExecutionJobService) {

    /**
     * Get the execution job by its id
     *
     * @param jobId the id of the execution job
     */
    @GetMapping("/{jobId}")
    fun getExecutionJob(@PathVariable jobId: Int): ResponseEntity<ExecutionJobEntity> {
        executionJobService.getExecutionJob(jobId)?.let {
            return ResponseEntity.ok(it)
        } ?: return ResponseEntity.notFound().build()
    }
}