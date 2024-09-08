package com.example.VacBook.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class exceptionHandle : ResponseEntityExceptionHandler(){

    companion object {
        private val log = LoggerFactory.getLogger(exceptionHandle::class.java)
    }

    @ExceptionHandler(Exception::class)
    private fun handleExceptions(ex: Throwable): ResponseEntity<Any>{
        log.info("Exception occurred: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    private fun error(): ResponseEntity<Any>{
        return ResponseEntity(ErrorResponse("Internal Server Error","Exception occurred"),HttpStatus.BAD_REQUEST)

    }

}