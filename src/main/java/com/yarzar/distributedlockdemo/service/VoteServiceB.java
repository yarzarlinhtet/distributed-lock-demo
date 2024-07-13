package com.yarzar.distributedlockdemo.service;

import com.yarzar.distributedlockdemo.constant.LName;
import com.yarzar.distributedlockdemo.constant.QName;
import com.yarzar.distributedlockdemo.entity.Type;
import com.yarzar.distributedlockdemo.entity.Vote;
import com.yarzar.distributedlockdemo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class VoteServiceB {

    private static final Logger LOG = LoggerFactory.getLogger(VoteServiceB.class);

    private final VoteRepository voteRepository;

    private final RedisLockRegistry lockRegistry;

    @RabbitListener(queues = QName.VOTE_QUEUE)
    public void voteListener(Type type) {
        LOG.info("VoteServiceB.voteListener() type: {}", type);
        var lock = lockRegistry.obtain(LName.LOCK_VOTE_SYNC);
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                this.vote(type);
            }
        } catch (InterruptedException e) {
            LOG.error("message: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private Vote vote(Type type) {
        LOG.info("vote() type: {}", type);
        Optional<Vote> voteOpt = this.voteRepository.findByType(type);

        if (voteOpt.isPresent()) {
            Vote vote = voteOpt.get();
            vote.setCount(vote.getCount() + 1);
            this.voteRepository.save(vote);
            return vote;
        }
        Vote vote = new Vote();
        vote.setCount(1L);
        vote.setType(type);
        this.voteRepository.save(vote);
        return vote;
    }
}
