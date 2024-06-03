package com.asidliar.userbalanceservice.util

import org.apache.kafka.common.serialization.Deserializer
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ListMapEntryDeserializer : Deserializer<List<Map.Entry<Int, Int>>> {

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}

    override fun deserialize(topic: String, data: ByteArray?): List<Map.Entry<Int, Int>>? {
        return data?.let {
            jacksonObjectMapper().readValue(it, object : TypeReference<List<Map.Entry<Int, Int>>>() {})
        }
    }

    override fun close() {}
}