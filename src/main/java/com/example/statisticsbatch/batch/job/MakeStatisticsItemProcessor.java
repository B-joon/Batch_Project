package com.example.statisticsbatch.batch.job;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MakeStatisticsItemProcessor implements ItemProcessor<StudentVO, StatisticsVO> {

    private static final Logger log = LoggerFactory.getLogger(MakeStatisticsItemProcessor.class);

    // 데이터 가공
    @Override
    public StatisticsVO process(StudentVO item) {

        StatisticsVO statisticsVO = new StatisticsVO();

        statisticsVO.setFavorite_subject(item.getStudent_favorite_subject());

        return statisticsVO;
    }
}
