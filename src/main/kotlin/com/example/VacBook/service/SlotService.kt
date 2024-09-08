package com.example.VacBook.service

import com.example.VacBook.data.Slot
import com.example.VacBook.repository.PersonRepository
import com.example.VacBook.repository.SlotRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class SlotService{

    companion object {
        private val log = LoggerFactory.getLogger(SlotService::class.java)
    }

    @Autowired
    private lateinit var slotRepository: SlotRepository

    @Autowired
    private lateinit var personRepository: PersonRepository


    fun getAllSlots(page:Int?,size:Int?,sortBy:String?): List<Slot> {
        val pageable = PageRequest.of(page ?: 0, size ?: 10, Sort.by(sortBy ?: "time")) //pagination and sorting
        log.info("Display all the slots")
        return slotRepository.findAll(pageable).toList();
    }


    fun createNewSlot(slot: Slot): Slot {
        log.info("Create Slot: ${slot.id}")
        return slotRepository.save(slot);
    }


    fun createSlots(n : Long){
        var t  = slotRepository.findTopByOrderByIdDesc();  //Date and time of the last slot available
        t = if(t>LocalDateTime.now()) t.plusMinutes(15) else LocalDateTime.now(); //checking if the date and time of last slot was in past to determine if the new slots are to be continued from the last slot or from now

        //extract date and time for the new slot
        var date = t.toLocalDate();
        var time = t.toLocalTime();
        if(time.hour>16){                           //check if the time is after 4 pm. If so shift the time of slot to next day at 10 am
            date = date.plusDays(1);
            time = LocalTime.of(10,0,0);
        }
        if(time.hour<9)     //check if the time if before 10am, and if so, shift it to 10 am
            time = LocalTime.of(10,0);

        log.info("Create $n slots from $time")
        for(i in 1..n){                                                     //Create n slots at interval of 15 mins starting from the new slot time
            slotRepository.createSlots(LocalDateTime.of(date, time));       // between 10 am and 4pm
            if(time < LocalTime.of(15,45)){
                time = time.plusMinutes(15);
            }
            else{
                date = date.plusDays(1);
                time = LocalTime.of(10,0,0);
            }
        }

    }


    fun displaySlots1(page:Int?,size:Int?,sortBy:String?): List<Slot> {
        val pageable = PageRequest.of(page ?: 0, size ?: 10, Sort.by(sortBy ?: "time"))

        log.info("Display slots available for slot 1 booking")
        return slotRepository.displaySlots(LocalDateTime.now(),pageable); //display available slots for dose 1
    }


    fun displaySlots2(page:Int?,size:Int?,sortBy:String?,mobile: Long): List<Slot> {
        val pageable = PageRequest.of(page ?: 0, size ?: 10, Sort.by(sortBy ?: "time"))

        var i:Long = personRepository.getDose1Slot(mobile);     //id of dose 1
        var s:Slot = slotRepository.getById(i);     //dose 1 slot
        var t : LocalDateTime? = s.time;    //time of dose 1
        t = t!!.plusDays(84);    //84 days after dose 1

        log.info("Display slots available for slot 2 booking for $mobile")
        return slotRepository.displaySlots(t,pageable); //display available slots after 84 days.
    }

}
