package com.yarzar.distributedlockdemo.controller;

import com.yarzar.distributedlockdemo.entity.Type;
import com.yarzar.distributedlockdemo.processor.VoteProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteProcess voteProcess;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> vote() {
        this.voteProcess.process();
        return ResponseEntity.ok("success");
    }


    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> countVote() {
        return ResponseEntity.ok(this.voteProcess.countVote(Type.DEMO));
    }

}
