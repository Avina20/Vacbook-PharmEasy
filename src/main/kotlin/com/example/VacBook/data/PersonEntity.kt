package com.example.VacBook.data

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.persistence.CascadeType.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Entity
@Table(name="Person")
data class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?,

    @NotBlank(message = "Name is required")
    var name : String,

    @Column(unique = true)
    @Length(min = 10, max = 10,message = "The number should have length 10")
    @NotBlank(message = "Mobile number is required")
    @Positive(message = "Mobile number should be positive")
    var mobile : Long,

    @OneToOne(cascade = arrayOf(ALL))
    @JoinColumn(name="Dose1_slotId")
    var dose1 : Slot?,

    @OneToOne(cascade = arrayOf(ALL))
    @JoinColumn(name="Dose2_slotId")
    var dose2 : Slot?,
){
    constructor() : this(-1,"test",0,null,null);
    constructor(n: String,m: Long,d1: Slot?,d2: Slot?) : this(null,n,m,d1,d2);
}