package com.asidliar.userbalanceservice.repositories

import com.asidliar.userbalanceservice.entities.ExecutionJobEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExecutionJobRepository: JpaRepository<ExecutionJobEntity, Int>