package com.test.createdatetest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Serv {
    private final Repo repo;

    public void save(String comment){
        DateTest test = DateTest.builder()
                .comment(comment)
                .build();
        repo.save(test);
    }
    
}
