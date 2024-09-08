package com.example.VacBook.controller

import com.example.VacBook.data.Slot
import com.example.VacBook.repository.SlotRepository
import com.example.VacBook.service.SlotService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.validation.Valid

@RestController
@RequestMapping("/")
public class SlotController()
{

    companion object {
        private val log = LoggerFactory.getLogger(SlotController::class.java)
    }

    @Autowired
    private lateinit var slotRepository: SlotRepository

    @Autowired
    private lateinit var slotService: SlotService


    @PostMapping("/nSlots")
    fun createSlots(@RequestBody n : Long):Long{
        slotService.createSlots(n);
        return n;
    }


    @GetMapping("/slots")
    fun getAllSlots(@RequestParam(required = false, value = "page") page:Int?,
                    @RequestParam(required = false, value = "size") size:Int?,
                    @RequestParam(required = false, value = "sortBy") sortBy:String?) : List<Slot> {
        return slotService.getAllSlots(page,size,sortBy);
    }


    @PostMapping("/slots")
    fun createNewSlot(@RequestBody slot:Slot) : Slot {
        return slotService.createNewSlot(slot);
    }


    @GetMapping("/viewSlot1")
    fun viewSlot1(@RequestParam(required = false, value = "page") page:Int?,
                  @RequestParam(required = false, value = "size") size:Int?,
                  @RequestParam(required = false, value = "sortBy") sortBy:String?) : List<Slot>{
        return slotService.displaySlots1(page,size,sortBy);
    }


    @GetMapping("/viewSlot2")
    fun viewSlot2(@Valid @RequestParam(value="mobile") mobile:Long,
                  @RequestParam(required = false, value = "page") page:Int?,
                  @RequestParam(required = false, value = "size") size:Int?,
                  @RequestParam(required = false, value = "sortBy") sortBy:String?) : List<Slot>{
        return slotService.displaySlots2(page,size,sortBy,mobile);
    }

    @DeleteMapping("deletePastSlots")
    fun deletePastSlots(@RequestParam(required = false, value = "date")d : LocalDate){
        var t : LocalDateTime = (if(d==null) LocalDateTime.now() else LocalDateTime.of(d, LocalTime.of(24,60,60)))
        slotRepository.deletePastSlots(LocalDateTime.now());
    }


}