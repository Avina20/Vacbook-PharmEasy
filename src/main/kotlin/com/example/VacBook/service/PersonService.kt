package com.example.VacBook.service

import com.example.VacBook.data.Person
import com.example.VacBook.data.Slot
import com.example.VacBook.repository.PersonRepository
import com.example.VacBook.repository.SlotRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PersonService {

    companion object {
        private val log = LoggerFactory.getLogger(PersonService::class.java)
    }

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var slotRepository: SlotRepository;


    fun savePerson(person: Person): Person {
        log.info("Insert person : ${person.id}");
        return personRepository.save(person);
    }


    fun bookSlot1(id:Long, name:String, mobile:Long): Person {
        var s : Slot = slotRepository.getById(id);  //slot of the id provided by user
        if(s.booked==true)   //checking if slot is available for booking
            throw Exception("Slot already booked. Re-try");
        if(s.time!! < LocalDateTime.now())  //checking if slot is in present or future
            throw Exception("The date has gone. Please try a later date")
        else{
            s.mobile = mobile      //updating the slot with the person's details
            s.booked = true
            slotRepository.updateSlot(id,mobile);
            var p = Person(null,name,mobile,s,null)  //adding the person to the database, with his new slot
            personRepository.save(p)
            log.info("Booked slot 1 : ${s.id} for Person : ${p.id}")
            return p
        }
    }


    fun bookSlot2(id: Long, mobile: Long): Person {
        var s : Slot = slotRepository.getById(id);   //slot of the id that person chose for dose 2
        var p : Person = personRepository.getByMobile(mobile); //extract person's details from his mobile

        if(s.booked==true)   //checking if slot is available for booking
            throw Exception("Slot already booked. Re-try");
        if(s.time!! < LocalDateTime.now())  //checking if slot is in present or future
            throw Exception("The date has gone. Please try a later date")

        s.booked=true  //updating the slot details indicating slot is booked and mobile number of person
        s.mobile=p.mobile
        slotRepository.updateSlot(id,mobile);
        s = slotRepository.getById(id);
        p.dose2=s   //adding the slot of dose2 in the person's data
        personRepository.updatePerson(p.id, s.id);
        p = personRepository.getById(p.id!!);
        log.info("Booked slot 2 : ${s.id} for Person : ${p.id}")
        return p;
    }

}