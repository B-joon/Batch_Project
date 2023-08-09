package com.example.statisticsbatch.persistence.mapper;

import com.example.statisticsbatch.persistence.vo.FollowerVO;

import java.time.LocalDateTime;
import java.util.List;

public interface FollowerMapper {

    List<FollowerVO> genderData(LocalDateTime create_at);

    List<FollowerVO> ageData(long age);

}
