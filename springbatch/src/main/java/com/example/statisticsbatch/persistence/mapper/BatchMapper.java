package com.example.statisticsbatch.persistence.mapper;

import com.example.statisticsbatch.persistence.vo.batch.AgeVO;
import com.example.statisticsbatch.persistence.vo.batch.GenderVO;

public interface BatchMapper {

    int setGender(GenderVO genderVO);

    int setAge(AgeVO ageVO);

}
