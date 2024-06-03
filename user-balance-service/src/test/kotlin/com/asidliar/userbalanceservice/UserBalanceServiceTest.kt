package com.asidliar.userbalanceservice

import com.asidliar.userbalanceservice.models.ExecutionJobBatch
import com.asidliar.userbalanceservice.models.ExecutionJobStatus
import com.asidliar.userbalanceservice.services.ExecutionJobService
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = ["classpath:sql/insert_user_balances.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserBalanceServiceTest(@Autowired private val client: TestRestTemplate) {

    private val log: Logger = LoggerFactory.getLogger(UserBalanceServiceTest::class.java)

    companion object {
        @JvmStatic private val SET_BALANCE_BATCH_URL = "/api/user/balance/batch"
    }

    @Autowired
    private lateinit var executionJobService: ExecutionJobService

    @Autowired
    private lateinit var adminClient: AdminClient

    @Value("\${spring.kafka.topic.name}")
    private lateinit var topicName: String

    @BeforeEach
    fun setup() {
        adminClient.createTopics(listOf(NewTopic(topicName, 3, 1.toShort())))
    }

    @Test
    fun setUserBalancesBatch__returns_200_status_code() {
        val jsonFile = File(javaClass.classLoader.getResource("100000_user_balances.json")?.toURI() ?: throw Exception("File not found"))
        val userBalances: String = jsonFile.readText()
        val beforeBatch: Long = System.currentTimeMillis()

        val request = createRequestBody(userBalances)

        val response: ResponseEntity<ExecutionJobBatch> = client.exchange(
            SET_BALANCE_BATCH_URL, HttpMethod.PUT, request, ExecutionJobBatch::class.java
        )
        assert(response.statusCode == HttpStatus.ACCEPTED)

        val executionJobBatch: ExecutionJobBatch = response.body!!
        executionJobService.waitForExecutionJobBatchToComplete(executionJobBatch)

        val afterBatch: Long = System.currentTimeMillis() - beforeBatch
        log.info("Time taken for batch operation: {} ms", afterBatch)
        assert(executionJobBatch.executionJobBatch.parallelStream().noneMatch { it.status == ExecutionJobStatus.FAILED })
    }

    @Test
    fun setUserBalancesBatch__returns_400_status_code() {
        val request = createRequestBody("{\"13\": -54}")

        val response: ResponseEntity<Any> = client.exchange(
            SET_BALANCE_BATCH_URL, HttpMethod.PUT, request, Any::class.java
        )

        assert(response.statusCode == HttpStatus.BAD_REQUEST)
    }

    @AfterEach
    fun cleanup() {
        adminClient.deleteTopics(listOf(topicName))
    }

    private fun createRequestBody(body: String): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity(body, headers)
    }
}
