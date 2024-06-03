package com.asidliar.userbalanceservice.util

import com.asidliar.userbalanceservice.exceptions.ExceptionMessageContainer
import org.springframework.stereotype.Component

/**
 * Validates user balances to ensure that they are not negative
 */
@Component
class UserBalanceValidator {

    fun validate(userBalances: Map<Int, Int>): ExceptionMessageContainer {
        val exceptionMessageContainer = ExceptionMessageContainer()
        userBalances.entries.parallelStream().forEach { (userId, balance) ->
            if (balance < 0) {
                exceptionMessageContainer.addExceptionMessage("Balance of user $userId is negative")
            }
        }

        return exceptionMessageContainer
    }
}