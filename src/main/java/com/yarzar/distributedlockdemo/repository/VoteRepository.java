package com.yarzar.distributedlockdemo.repository;

import com.yarzar.distributedlockdemo.entity.Type;
import com.yarzar.distributedlockdemo.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByType(Type type);
}
