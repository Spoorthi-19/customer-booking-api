package com.example.api.Model

import javax.persistence.*


@Entity
public class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var customerId: Int,
    private var name: String,
    private var phno: String,
    private var city: String,
    @OneToMany(cascade = [CascadeType.REMOVE],mappedBy="customer", fetch = FetchType.LAZY)
    val booking: List<Booking> =emptyList()
)
{
    fun getId(): Int {
        return customerId
    }
    fun setId(id: Int) {
        this.customerId = id
    }
    fun getName(): String {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }
    fun getPhno(): String {
        return phno
    }
    fun setPhno(phno: String) {
        this.phno = phno
    }
    fun getCity(): String {
        return city
    }
    fun setCity(city: String) {
        this.city = city
    }
}