package com.example.payments.controller

import com.example.payments.model.Payment
import com.example.payments.service.PaymentService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/payments")
class PaymentController(private val service: PaymentService) {

    @GetMapping
    fun listPayments(
        @RequestParam(required = false) date: String?,
        @RequestParam(required = false) recipient: String?
    ): Map<String, Any> {
        val afterDate: LocalDate? = date?.let { LocalDate.parse(it) }
        val filtered: List<Payment> = service.getPayments(afterDate, recipient)

        return mapOf(
            "payments" to filtered,
            "totalAmount" to service.getTotalAmount(filtered)
        )
    }
}
