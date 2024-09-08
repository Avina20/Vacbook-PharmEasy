package com.example.VacBook.repository

import com.example.VacBook.data.Slot
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
interface SlotRepository : JpaRepository <Slot,Long> {

    @Query("SELECT * FROM slots WHERE s.booked=false",nativeQuery = true)
    fun displayAvailableSlots() : List<Slot>

    @Modifying
    @Transactional
    @Query("INSERT INTO slots(time,booked,mobile) VALUES(?1,false,null)",nativeQuery = true)
    fun createSlots(time:LocalDateTime)

    @Query("SELECT time FROM slots ORDER BY id DESC LIMIT 1",nativeQuery = true)
    fun findTopByOrderByIdDesc() : LocalDateTime

    @Query("SELECT * FROM Slots WHERE booked=false AND time>?1",nativeQuery = true)
    fun displaySlots(time:LocalDateTime, pageable: Pageable) : List<Slot>

    @Modifying
    @Transactional
    @Query("UPDATE slots SET booked=true, mobile=?2 WHERE id=?1",nativeQuery = true)
    fun updateSlot(id:Long?, mobile:Long)

    @Modifying
    @Transactional
    @Query("DELETE FROM slots WHERE time<?1 AND booked=false",nativeQuery = true)
    fun deletePastSlots(time : LocalDateTime)
}