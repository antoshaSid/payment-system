package com.asidliar.userbalanceservice.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.common.serialization.Serializer

class ListMapEntrySerializer : Serializer<List<Map.Entry<Int, Int>>> {

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}

    override fun serialize(topic: String, data: List<Map.Entry<Int, Int>>?): ByteArray? {
        return data?.let { jacksonObjectMapper().writeValueAsBytes(it) }
    }

    override fun close() {}
}