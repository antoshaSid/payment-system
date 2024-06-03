package com.asidliar.userbalanceservice.repositories

import com.asidliar.userbalanceservice.entities.UserBalanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for user balance operations
 */
@Repository
interface UserBalanceRepository: JpaRepository<UserBalanceEntity, Int>