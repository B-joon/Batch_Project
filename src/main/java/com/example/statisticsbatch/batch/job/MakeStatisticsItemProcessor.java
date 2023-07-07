package com.example.statisticsbatch.batch.job;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.springframework.batch.item.ItemProcessor;

public class MakeStatisticsItemProcessor implements ItemProcessor<StudentVO, StatisticsVO> {
    @Override
    public StatisticsVO process(StudentVO item) throws Exception {
        return null;
    }
}
