package com.example.payments.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Payment(
    val id: String,
    val amount: Double,
    val currency: String,
    @JsonProperty("scheduled_date")
    val scheduledDate: LocalDate,
    val recipient: String,
    var within24Hours: Boolean = false
)