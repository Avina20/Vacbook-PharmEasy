/*package com.example.VacBook.controller

import com.example.VacBook.data.Person
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.connect.json.JsonSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class KafkaProducer {
    companion object {
        private val log = LoggerFactory.getLogger(KafkaProducer::class.java)
    }

    @Autowired
    constructor(kafkaTemplate: KafkaTemplate<String, Person>) {
        this.kafkaTemplate = kafkaTemplate
    }
    var kafkaTemplate: KafkaTemplate<String, Person>? = null;
    val topic: String = "topic1"

    @GetMapping("/sendJson")
    fun sendJson(@RequestBody person: Person):ResponseEntity<Person>{
        var lf: ListenableFuture<SendResult<String, Person>> = kafkaTemplate?.send("topic1", person)!!
        var sendResult: SendResult<String, Person> = lf.get()
        return ResponseEntity.ok(sendResult.producerRecord.value())
    }

}*/