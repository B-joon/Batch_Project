package com.example.statisticsbatch.batch.gender;

import com.example.statisticsbatch.persistence.vo.FollowerVO;
import com.example.statisticsbatch.persistence.vo.batch.GenderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * 호출한 데이터를 가공하기 위한 class
 */

public class MakeGenderBatchItemProcessor implements ItemProcessor<FollowerVO, GenderVO> {

    private static final Logger log = LoggerFactory.getLogger(MakeGenderBatchItemProcessor.class);

    // 데이터 가공
    @Override
    public GenderVO process(FollowerVO item) {

        log.info(item.toString());

        GenderVO gender = new GenderVO();

        if (item.getGender().equals("M")) {
            gender.setGender("M");
            gender.setCount(item.getCount());
        } else {
            gender.setGender("F");
            gender.setCount(item.getCount());
        }

        return gender;
    }
}
