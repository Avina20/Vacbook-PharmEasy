package com.example.VacBook.repository

import com.example.VacBook.data.Person
import com.example.VacBook.data.Slot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
interface PersonRepository : JpaRepository<Person,Long>{

    @Query("SELECT dose1_slot_id FROM Person WHERE mobile=?1",nativeQuery = true)
    fun getDose1Slot(mobile:Long) : Long

    fun getByMobile(mobile: Long) : Person

    @Modifying
    @Transactional
    @Query("UPDATE person SET dose2_slot_id=?2 WHERE id=?1",nativeQuery = true)
    fun updatePerson(id:Long?, s:Long?)

}