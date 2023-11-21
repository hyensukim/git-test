package com.test.createdatetest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DateTest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String comment;

    @Temporal(TemporalType.DATE)
    private Date date1;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date2 ;

    @CreatedDate
    private LocalDateTime date3;

    @CreationTimestamp
    private LocalDateTime date4;
}
