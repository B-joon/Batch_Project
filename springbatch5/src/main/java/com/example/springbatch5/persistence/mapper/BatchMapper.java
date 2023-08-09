package com.example.springbatch5.persistence.mapper;

import com.example.springbatch5.persistence.vo.batch.AgeVO;
import com.example.springbatch5.persistence.vo.batch.GenderVO;

public interface BatchMapper {

    int setGender(GenderVO genderVO);

    int setAge(AgeVO ageVO);

}
