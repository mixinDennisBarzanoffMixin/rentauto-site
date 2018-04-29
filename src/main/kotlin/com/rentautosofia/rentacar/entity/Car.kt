package com.rentautosofia.rentacar.entity

import com.rentautosofia.rentacar.transmission.Transmission
import javax.persistence.*

@Entity
@Table(name = "cars")
data class Car(@Column var name: String = "",
               @Column var price: Int = 0,
               @Column(length = 1000) var imgURL: String = "",
               @Column var LPG: Boolean? = false,
               @Column @Enumerated(EnumType.STRING)
               var transmission: Transmission = Transmission.MANUAL) {

    fun getPricePerDayFor(days: Int) = when (days) {
        in 1 .. 3 -> this.price
        in 4 .. 8 -> this.price - 7
        in 9 .. 15 -> this.price - 15
        in 16 .. Int.MAX_VALUE -> this.price - 20
        else -> throw IllegalArgumentException("Cannot get get price per day for $days (NON-POSITIVE) days!!")
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}

fun car(function: Car.() -> Unit): Car {
    val car = Car()
    car.function()
    return car
}