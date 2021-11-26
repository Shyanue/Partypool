package com.se.partypool.repository

import com.se.partypool.entity.Image
import org.springframework.data.repository.CrudRepository

interface ImageRepository:CrudRepository<Image, Long> {
}