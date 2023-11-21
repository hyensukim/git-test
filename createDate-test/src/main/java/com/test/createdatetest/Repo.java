package com.test.createdatetest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<DateTest, Long> {
}
