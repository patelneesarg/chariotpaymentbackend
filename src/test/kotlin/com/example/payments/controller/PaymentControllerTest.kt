package com.example.payments.controller

import com.example.payments.model.Payment
import com.example.payments.service.PaymentService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@WebMvcTest(PaymentController::class)
class PaymentControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var service: PaymentService  // ✅ Spring injects a Mockito mock

    private val samplePayments = listOf(
        Payment("txn1", 1000.0, "USD", LocalDate.now(), "John", true)
    )

    @Test
    fun `GET api payments returns JSON with totalAmount`() {
        `when`(service.getPayments(null, null)).thenReturn(samplePayments)
        `when`(service.getTotalAmount(samplePayments)).thenReturn(1000.0)

        mockMvc.perform(get("/api/payments")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.payments[0].id").value("txn1"))
            .andExpect(jsonPath("$.totalAmount").value(1000.0))
    }

    @Test
    fun `GET api payments with filters`() {
        val filtered = samplePayments
        `when`(service.getPayments(LocalDate.now(), "John")).thenReturn(filtered)
        `when`(service.getTotalAmount(filtered)).thenReturn(1000.0)

        mockMvc.perform(
            get("/api/payments")
                .param("after", LocalDate.now().toString())
                .param("recipient", "John")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.payments[0].recipient").value("John"))
    }
}
