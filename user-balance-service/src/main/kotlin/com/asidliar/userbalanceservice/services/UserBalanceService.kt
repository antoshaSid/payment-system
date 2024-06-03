package com.asidliar.userbalanceservice.services

import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import java.util.concurrent.ConcurrentMap

/**
 * Service for user balance operations
 */
interface UserBalanceService {

    /**
     * Batch operation updating balances of all users provided with the input map.
     * If the input map contains non-existing users, they will be silently ignored.
     *
     * @param userBalances the map with a user id as key and a new balance as value
     */
    fun setUserBalancesBatch (userBalances: ConcurrentMap<Int, Int>): ExecutionJobBatch
}