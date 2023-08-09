package com.example.springbatch5.persistence.mapper;

import com.example.springbatch5.persistence.vo.FollowerVO;

import java.time.LocalDateTime;
import java.util.List;

public interface FollowerMapper {

    List<FollowerVO> genderData(LocalDateTime create_at);

    List<FollowerVO> ageData(long age);

}
