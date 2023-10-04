package com.example.springintegrationlockoracle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@Testcontainers
class OracleSuperServiceTest {
    @Container
    @ServiceConnection
    private static final OracleContainer oracleContainer
            = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:21-slim-faststart"))
            .withInitScript("oracle.sql");

    @MockBean
    SpecialService specialService;

    @Autowired
    SuperService superService;

    @Test
    void power() throws InterruptedException {
        List<String> callOrder = new ArrayList<>();
        doAnswer((invocation) -> {
            String bar = invocation.getArgument(1);
            callOrder.add(bar);

            if (bar.equals("bobo")) {
                Thread.sleep(1000);
            }

            return null;
        }).when(specialService).special(anyString(), anyString());

        Thread bobo = new Thread(() -> {
            superService.power("foo", "bobo");
        });

        Thread baba = new Thread(() -> {
            superService.power("foo", "baba");
        });

        bobo.start();
        Thread.sleep(100);
        baba.start();

        bobo.join();
        baba.join();

        assertEquals(List.of("bobo", "baba"), callOrder);
    }
}