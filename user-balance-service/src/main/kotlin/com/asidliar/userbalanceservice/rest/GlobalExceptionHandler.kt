package com.asidliar.userbalanceservice.rest

import com.asidliar.userbalanceservice.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Global exception handler for the REST controllers
 */
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ResponseEntity<List<String>> {
        return ResponseEntity(e.getExceptionsMessages(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<Any> {
        return ResponseEntity("Sorry, unexpected exception occurred: ${e.message}",
            HttpStatus.INTERNAL_SERVER_ERROR)
    }
}