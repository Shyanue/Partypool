package com.se.partypool.repository

import org.springframework.data.repository.CrudRepository

interface ReservationRepository: CrudRepository<Reservation, Long> {
}