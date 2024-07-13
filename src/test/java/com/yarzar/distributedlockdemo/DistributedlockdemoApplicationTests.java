package com.yarzar.distributedlockdemo;

import com.yarzar.distributedlockdemo.entity.Type;
import com.yarzar.distributedlockdemo.processor.VoteProcess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DistributedlockdemoApplicationTests {

    @Autowired
    private VoteProcess voteProcess;

    @Test
    void contextLoads() {

    }

    @Test
    void test_multiple_lVote() throws InterruptedException {




        Thread.sleep(10000);

        this.voteProcess.countVote(Type.DEMO);
    }


}
