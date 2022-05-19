package com.example.api.Service


import com.example.api.Controller.request.BookingRequest
import com.example.api.Exception.EntityNotFoundException
import com.example.api.Model.Booking
import com.example.api.Model.Customer
import com.example.api.Repository.BookingRepository
import com.example.api.Repository.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.*

internal class BookingServiceTest{
    private val customerRepository=mockk<CustomerRepository>()
    private val bookingRepository=mockk<BookingRepository>()
    private val bookingService=BookingService(customerRepository,bookingRepository)

    @Test
    fun `Should create booking`(){
        every { customerRepository.findById(fakeCustomer.customerId) } returns Optional.of(fakeCustomer)
        every { bookingRepository.save(fakeBooking) } returns fakeBooking
        bookingService.addBooking(fakeCustomer.customerId, fakeBookingRequest)

        verify { bookingRepository.save(fakeBooking) }
    }

    @Test
    fun `Should throw 404 to create booking for non-existent customer`(){
        every { customerRepository.findById(fakeCustomer.customerId) } returns Optional.empty()

        invoking { bookingService.addBooking(fakeCustomer.customerId, fakeBookingRequest) } shouldThrow EntityNotFoundException("Customer not found")

    }

    @Test
    fun `Should get the booking of a customer`(){
        every { customerRepository.findById(1) } returns Optional.of(fakeCustomer.copy(customerId = 1))
        every { bookingRepository.findByCustomerCustomerIdAndId(1, fakeBooking.id) } returns Optional.of(fakeBooking)

        val fetchedBooking = bookingService.getBooking(1, fakeBooking.id)

        fetchedBooking `should be equal to` fakeBooking
    }

    @Test
    fun `Should throw 404 for getting the booking of non-existent customer`(){
        every { customerRepository.findById(fakeCustomer.customerId) } returns Optional.empty()

        invoking { bookingService.getBooking(fakeCustomer.customerId, fakeBooking.id) }  shouldThrow EntityNotFoundException("Customer not found")
    }

    @Test
    fun `Should delete a booking`(){
        every {customerRepository.existsById(fakeCustomer.customerId)} returns true
        every {bookingRepository.deleteById(fakeBooking.id)}returns Unit

        bookingService.deleteBooking(fakeCustomer.customerId, fakeBooking.id)

        verify { bookingRepository.deleteById(fakeBooking.id) }
    }

    @Test
    fun `Should throw 404 for deleting the booking of non-existent customer`(){
        every {customerRepository.existsById(fakeCustomer.customerId)} returns false

        invoking { bookingService.deleteBooking(fakeCustomer.customerId, fakeBooking.id) }shouldThrow EntityNotFoundException("Customer not found")
    }

    @Test
    fun `Should update a booking`(){
        every { customerRepository.findById(fakeCustomer.customerId) } returns Optional.of(fakeCustomer)
        every { bookingRepository.findByCustomerCustomerIdAndId(fakeCustomer.customerId, fakeBooking.id) }returns Optional.of(fakeBooking)
        every { bookingRepository.save(fakeBooking.copy(Name = "Jen")) }returns fakeBooking.copy(Name = "Jen")

        bookingService.updateBooking(fakeCustomer.customerId, fakeBooking.id, fakeBookingRequest.copy(Name = "Jen"))

        verify { bookingRepository.save(fakeBooking.copy(Name = "Jen")) }
    }

    @Test
    fun `Should throw 404 for updating the booking of non-existent customer`(){
        every {customerRepository.findById(fakeCustomer.customerId)} returns Optional.empty()
        every { bookingRepository.findByCustomerCustomerIdAndId(fakeCustomer.customerId, fakeBooking.id) } returns Optional.of(fakeBooking)
        invoking { bookingService.updateBooking(fakeCustomer.customerId, fakeBooking.id, fakeBookingRequest)}shouldThrow EntityNotFoundException("Customer not found")
    }

}

private val fakeCustomer= Customer(customerId = 0, name = "Jack", phno = "99999", city = "Bangalore")
private val fakeBooking=Booking(id = 10, Name = "John", date = "2022-05-18", room = "103", customer = fakeCustomer)
private val fakeBookingRequest=BookingRequest(id = 10, Name = "John", date = "2022-05-18", room = "103")
