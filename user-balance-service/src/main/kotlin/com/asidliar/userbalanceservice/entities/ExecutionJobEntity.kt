package com.asidliar.userbalanceservice.entities

import com.asidliar.userbalanceservice.models.ExecutionJobStatus
import jakarta.persistence.*

@Entity
@Table(name = "EXECUTION_JOBS")
data class ExecutionJobEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    val status: ExecutionJobStatus = ExecutionJobStatus.CREATED
)