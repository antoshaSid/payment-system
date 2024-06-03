package com.asidliar.userbalanceservice.services

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import com.asidliar.userbalanceservice.services.producers.UserBalanceUpdateProducer
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentMap

@Service
@Transactional
class UserBalanceServiceImpl(private val userBalanceUpdateProducer: UserBalanceUpdateProducer): UserBalanceService {

    override fun setUserBalancesBatch (userBalances: ConcurrentMap<Int, Int>): ExecutionJobBatch {
        val jobList: List<ExecutionJobEntity> = userBalances.entries.chunked(1000).parallelStream()
            .map(userBalanceUpdateProducer::sendUserBalanceUpdateBatch)
            .toList()

        return ExecutionJobBatch(jobList)
    }
}