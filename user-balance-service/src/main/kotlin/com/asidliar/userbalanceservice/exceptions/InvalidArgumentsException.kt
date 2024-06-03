package com.asidliar.userbalanceservice.exceptions

class ValidationException(private val exceptionMessageContainer: ExceptionMessageContainer): RuntimeException() {
    fun getExceptionsMessages(): List<String> {
        return exceptionMessageContainer.getExceptionsMessages()
    }
}