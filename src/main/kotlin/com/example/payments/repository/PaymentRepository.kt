package com.example.payments.repository

import com.example.payments.model.Payment
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository
import java.io.InputStream

@Repository
class PaymentRepository {

    private lateinit var payments: List<Payment>

    @PostConstruct
    fun init() {
        val mapper = ObjectMapper().registerKotlinModule()
        mapper.findAndRegisterModules()
        val inputStream: InputStream = this::class.java.getResourceAsStream("/payments.json")
            ?: throw IllegalStateException("payments.json not found")
        payments = mapper.readValue(inputStream, object : TypeReference<List<Payment>>() {})
    }

    fun findAll(): List<Payment> = payments
}
