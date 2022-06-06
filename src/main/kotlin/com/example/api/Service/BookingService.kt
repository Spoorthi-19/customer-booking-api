package com.example.api.Service
//import com.example.api.*
import com.example.api.Controller.request.BookingRequest
import com.example.api.Exception.EntityNotFoundException
import com.example.api.Model.Booking
//import com.example.api.Model.BookingRequest
import com.example.api.Repository.BookingRepository
//import com.example.api.Repository.BookingRequestRepository
import com.example.api.Repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
 class BookingService(private val customerRepository: CustomerRepository,
                                private val bookingRepository: BookingRepository) {

     fun addBooking(customerId: Int, book: BookingRequest) {
        val customer = customerRepository.findById(customerId)
        if(customer.isEmpty){
            throw EntityNotFoundException("customer not found.")
        }
            bookingRepository.save(
                Booking(
                    id = book.id,
                    name = book.name,
                    date = book.date,
                    room = book.room,
                    customer = customer.get()

                    )
            )

        }

     fun getBooking(customerId: Int,id: Int): Booking {
        val customer = customerRepository.findById(customerId)
        if(customer.isEmpty){
            throw EntityNotFoundException("customer not found.")
        }
        return bookingRepository.findByCustomerCustomerIdAndId(customerId,id)
            .orElseThrow{ EntityNotFoundException("Booking not found.")}
    }


    fun deleteBooking(customerId: Int,id: Int) {
        val customer= customerRepository.findById(customerId)
        if(customer.isEmpty){
            throw EntityNotFoundException("customer not found.")
        }
        bookingRepository.deleteById(id)
    }

        fun updateBooking(customerId: Int,id: Int, book: BookingRequest){
        book.id=id
        val customer = customerRepository.findById(customerId)
        val booking = bookingRepository.findByCustomerCustomerIdAndId(customerId, id)
        if(customer.isEmpty){
            throw EntityNotFoundException("customer not found.")
        }
        if(booking.isEmpty){
            throw EntityNotFoundException("booking not found.")
        }
        bookingRepository.save(
            Booking(
                id = book.id,
                name = book.name,
                date = book.date,
                room = book.room,
                customer = customer.get()
            )
        )
    }


}