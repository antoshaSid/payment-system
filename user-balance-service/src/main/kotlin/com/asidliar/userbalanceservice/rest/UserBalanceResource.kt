package com.asidliar.userbalanceservice.rest

import com.asidliar.userbalanceservice.exceptions.ValidationException
import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import com.asidliar.userbalanceservice.services.UserBalanceService
import com.asidliar.userbalanceservice.util.UserBalanceValidator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentMap

/**
 * Resource for user balance operations
 */
@RestController
@RequestMapping("/user/balance", produces = ["application/json"], consumes = ["application/json"])
class UserBalanceResource(private val userBalanceService: UserBalanceService,
                          private val userBalanceValidator: UserBalanceValidator) {

    /**
     * Batch operation updating balances of all users provided with the input map.
     * If the input map contains non-existing users, they will be silently ignored.
     *
     * @param userBalances the map with a user id as key and a new balance as value
     * @return the execution job batch
     */
    @PutMapping("/batch")
    fun setUserBalancesBatch (@RequestBody userBalances: ConcurrentMap<Int, Int>): ResponseEntity<ExecutionJobBatch> {
        val exceptionMessageContainer = userBalanceValidator.validate(userBalances)

        if (!exceptionMessageContainer.isValid()) {
            throw ValidationException(exceptionMessageContainer)
        }

        val executionJobBatch: ExecutionJobBatch = userBalanceService.setUserBalancesBatch(userBalances)
        return ResponseEntity(executionJobBatch, HttpStatus.ACCEPTED)
    }
}