package com.example.VacBook.data

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name="Slots")
data class Slot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var time : LocalDateTime? = LocalDateTime.of(LocalDate.now(), LocalTime.of(12,45)),

    var booked : Boolean?,

    var mobile : Long?,
){
    constructor() : this(-1, LocalDateTime.of(LocalDate.now(), LocalTime.of(9,45)),false,null);
}