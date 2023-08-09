package com.example.statisticsbatch.persistence.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FollowerVO {

    private long seq;
    private String id;
    private String name;
    private String gender;
    private long age;
    private LocalDateTime create_at;

    private long count;

}
