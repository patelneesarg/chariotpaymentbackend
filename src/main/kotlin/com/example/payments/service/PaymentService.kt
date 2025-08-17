package com.example.payments.service

import com.example.payments.model.Payment
import com.example.payments.repository.PaymentRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class PaymentService(private val repository: PaymentRepository) {

    fun getPayments(after: LocalDate?, recipient: String?): List<Payment> {
        val today = LocalDate.now()

        return repository.findAll()
            .filter { after == null || !it.scheduledDate.isBefore(after) }
            .filter { recipient == null || it.recipient.equals(recipient, ignoreCase = true) }
            .map { payment ->
                val daysBetween = ChronoUnit.DAYS.between(today, payment.scheduledDate)
                payment.copy(within24Hours = (daysBetween == 0L))
            }
    }

    fun getTotalAmount(payments: List<Payment>): Double =
        payments.sumOf { it.amount }
}
