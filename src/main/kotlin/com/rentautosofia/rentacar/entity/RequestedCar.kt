package com.rentautosofia.rentacar.entity

import java.util.*
import javax.persistence.*

@Entity
data class RequestedCar(override var carId: Int = 0,
                        override var customerId: Int = 0,
                        override var startDate: Date = Date(),
                        override var endDate: Date = Date(),
                        @Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        override var id: Int = 0) : BaseBooking(carId, customerId, startDate, endDate) {


    override fun equals(other: Any?): Boolean {
        if (other !is RequestedCar) {
            return false
        }
        if ((other.carId == this.carId) and
                (other.startDate == this.startDate) and
                (other.endDate == this.endDate)) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = carId
        result = 31 * result + customerId
        result = 31 * result + startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + id
        return result
    }
}


fun requestedCar(function: RequestedCar.() -> Unit): RequestedCar {
    val requestedCar = RequestedCar()
    requestedCar.function()
    return requestedCar
}
