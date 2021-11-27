package com.se.partypool.repository

import com.se.partypool.entity.Place
import org.springframework.data.repository.CrudRepository

interface PlaceRepository:CrudRepository<Place, Long> {
}