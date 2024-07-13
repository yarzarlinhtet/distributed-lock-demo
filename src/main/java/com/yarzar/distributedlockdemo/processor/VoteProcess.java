package com.yarzar.distributedlockdemo.processor;

import com.yarzar.distributedlockdemo.constant.QName;
import com.yarzar.distributedlockdemo.entity.Type;
import com.yarzar.distributedlockdemo.entity.Vote;
import com.yarzar.distributedlockdemo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Component
public class VoteProcess {

    private static final Logger LOG = LoggerFactory.getLogger(VoteProcess.class);

    private final RabbitTemplate rabbitTemplate;

    private final VoteRepository voteRepository;

    public void process() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 100; i++) {
            executor.submit(() -> this.vote(Type.DEMO));
        }
        executor.shutdown();
    }

    public void vote(Type type) {
        this.rabbitTemplate.convertAndSend(QName.VOTE_QUEUE, type);
    }

    public Vote countVote(Type type) {
        Vote vote = this.voteRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("vote not found!!"));
        LOG.info("countVote() vote: {}", vote);

        return vote;
    }
}
