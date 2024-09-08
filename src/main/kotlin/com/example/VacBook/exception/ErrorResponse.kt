package com.example.VacBook.exception

data class ErrorResponse(val error: String, val message: String) {
    constructor() : this("", "")
}