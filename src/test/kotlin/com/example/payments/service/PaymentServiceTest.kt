package com.example.payments.service

import com.example.payments.model.Payment
import com.example.payments.repository.PaymentRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PaymentServiceTest {

    private val repo = mockk<PaymentRepository>()
    private val service = PaymentService(repo)

    private val samplePayments = listOf(
        Payment("txn1", 1000.0, "USD", LocalDate.now(), "John"),
        Payment("txn2", 2000.0, "USD", LocalDate.now().plusDays(1), "Jane"),
        Payment("txn3", 1500.0, "USD", LocalDate.now().minusDays(1), "John")
    )

    init {
        every { repo.findAll() } returns samplePayments
    }

    @Test
    fun `getPayments filters by recipient`() {
        val filtered = service.getPayments(null, "John")
        assertEquals(2, filtered.size)
        assertTrue(filtered.all { it.recipient == "John" })
    }

    @Test
    fun `getPayments filters by after date`() {
        val date = LocalDate.now()
        val filtered = service.getPayments(date, null)
        assertEquals(2, filtered.size)
        assertTrue(filtered.all { !it.scheduledDate.isBefore(date) })
    }

    @Test
    fun `getPayments marks within24Hours correctly`() {
        val filtered = service.getPayments(null, null)
        val today = LocalDate.now()
        filtered.forEach {
            if (it.scheduledDate == today) {
                assertTrue(it.within24Hours)
            } else {
                assertFalse(it.within24Hours)
            }
        }
    }

    @Test
    fun `getTotalAmount calculates sum`() {
        val filtered = service.getPayments(null, null)
        val total = service.getTotalAmount(filtered)
        assertEquals(4500.0, total)
    }
}
