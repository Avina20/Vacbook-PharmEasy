package com.example.VacBook.controller

import com.example.VacBook.data.Person
import com.example.VacBook.repository.PersonRepository
import com.example.VacBook.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/")
class PersonController {

    companion object {
        private val log = LoggerFactory.getLogger(PersonController::class.java)
    }

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var personService: PersonService


    @PostMapping("/addPerson")
    fun savePerson(@RequestBody person : Person) : Person {
        return personService.savePerson(person);
    }


    @GetMapping("/displayPerson")
    fun getPerson() : List<Person> {
        log.info("Display all the persons");
        return personRepository.findAll().toList();
    }


    @PostMapping("/bookSlot1")
    fun bookSlot1(@RequestParam(value="id") id:Long,
                  @Valid @RequestParam(value="name") name:String,
                  @Valid @RequestParam(value="mobile") mobile:Long) : Person {
        return personService.bookSlot1(id,name,mobile);
    }


    @PostMapping("/bookSlot2")
    fun bookSlot2(@RequestParam(value="id") id: Long,
                  @Valid @RequestParam(value="mobile") mobile:Long) : Person {
        return personService.bookSlot2(id,mobile);
    }

}