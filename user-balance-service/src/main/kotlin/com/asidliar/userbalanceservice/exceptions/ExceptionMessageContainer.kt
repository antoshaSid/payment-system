package com.asidliar.userbalanceservice.exceptions

class ExceptionMessageContainer {
    private val exceptionsMessages: MutableList<String> = mutableListOf()

    fun addExceptionMessage(exceptionMessage: String) {
        exceptionsMessages.add(exceptionMessage)
    }

    fun getExceptionsMessages(): List<String> {
        return exceptionsMessages
    }

    fun isValid(): Boolean {
        return exceptionsMessages.isEmpty()
    }
}