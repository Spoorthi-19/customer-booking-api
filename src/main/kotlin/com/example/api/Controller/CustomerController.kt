package com.example.api.Controller

//import com.example.api.*
//import com.example.api.Controller.request.BookingRequest
//import com.example.api.Model.Booking
//import com.example.api.Model.BookingRequest
import com.example.api.Model.Customer
//import com.example.api.Service.BookingService
import com.example.api.Service.CustomerService
import org.springframework.web.bind.annotation.*
import org.springframework.http.*
//import java.util.*

@RestController
@RequestMapping("/customer")
 class CustomerController(private var customerService: CustomerService) {

    @PostMapping
    fun addNewCustomer(@RequestBody customer: Customer ): ResponseEntity<Customer>{
        customerService.addDetails(customer)
        return ResponseEntity.ok(customer)
    }

    @GetMapping
    fun get() : ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(customerService.get())

    }

    @GetMapping("/{customerId}")
    fun getCustomer(@PathVariable customerId: Int): ResponseEntity<Customer> {
        return ResponseEntity.ok(customerService.getCustomer(customerId))
    }

    @PutMapping("/{customerId}")
    fun updateCustomer(@PathVariable customerId: Int, @RequestBody customer: Customer): ResponseEntity<Customer> {
        customerService.updateCustomer(customerId,customer)
        return ResponseEntity.ok((customer))
    }
    @DeleteMapping("/{customerId}")
    fun deleteCustomer(@PathVariable customerId:Int){
        customerService.deleteCustomer(customerId)
    }

//booking as a sub resource


}