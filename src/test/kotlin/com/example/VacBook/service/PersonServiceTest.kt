package com.example.VacBook.service

import com.example.VacBook.data.Person
import com.example.VacBook.data.Slot
import com.example.VacBook.repository.PersonRepository
import com.example.VacBook.repository.SlotRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime

@SpringBootTest
class PersonServiceTest {

    @MockBean
    private lateinit var personRepository: PersonRepository

    @MockBean
    private lateinit var slotRepository: SlotRepository;

    @Autowired
    private lateinit var personService: PersonService;

    var time : LocalDateTime = LocalDateTime.now().plusDays(1);

    @BeforeEach
    fun setUp() {

        var s = Slot(1, time,false,null);
        var p = Person(1,"Avina",9999999999,null,null);
        Mockito.`when`(slotRepository.getById(1)).thenReturn(s);
        Mockito.doNothing().`when`(slotRepository).updateSlot(1,9999999999);
        Mockito.`when`(personRepository.save(p)).thenReturn(null);
        p.dose1=s;
        var ss = Slot(2, time,false,null);
        Mockito.`when`(personRepository.getByMobile(9999999999)).thenReturn(p);
        Mockito.`when`(slotRepository.getById(2)).thenReturn(ss);
        Mockito.doNothing().`when`(slotRepository).updateSlot(2,9999999999);
        Mockito.doNothing().`when`(personRepository).updatePerson(1,2);
        p.dose2=ss;
        Mockito.`when`(personRepository.getById(1)).thenReturn(p);
    }

    @Test
    @DisplayName("Test if slot for dose 1 updates in Person successfully")
    fun bookSlot1Test() {
        var s = Slot(1, time,true,9999999999);
        var p :Person = personService.bookSlot1(1,"Avina",9999999999);

        assertEquals(s,p.dose1);
    }

    @Test
    @DisplayName("Test if slot for dose 2 updates in Person")
    fun bookSlot2Test() {
        var p : Person = personService.bookSlot2(2,9999999999);
        var ss = Slot(2, time,true,9999999999);
        assertEquals(p.dose2,ss)
    }
}