package com.example.payments

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SpaController {
    @RequestMapping(value = ["/{path:[^\\.]*}"])
    fun forward(): String {
        return "forward:/index.html"
    }
}
