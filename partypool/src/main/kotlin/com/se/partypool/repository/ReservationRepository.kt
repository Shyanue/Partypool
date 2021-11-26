package com.se.partypool.repository

import com.se.partypool.entity.Reservation
import org.springframework.data.repository.CrudRepository

interface ReservationRepository:CrudRepository<Reservation, Long> {
}