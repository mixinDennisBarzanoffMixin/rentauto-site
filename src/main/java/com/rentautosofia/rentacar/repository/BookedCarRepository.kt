package com.rentautosofia.rentacar.repository

import com.rentautosofia.rentacar.entity.BookedCar
import org.springframework.data.jpa.repository.JpaRepository

interface BookedCarRepository : JpaRepository<BookedCar, Int>

