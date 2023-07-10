package com.example.statisticsbatch.batch.job;

import com.example.statisticsbatch.batch.mapper.StatisticsMapper;
import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeStatisticsItemProcessor implements ItemProcessor<StudentVO, StatisticsVO> {

    private static final Logger log = LoggerFactory.getLogger(MakeStatisticsItemProcessor.class);

    // 데이터 가공
    @Override
    public StatisticsVO process(StudentVO item) throws Exception {

        StatisticsVO statisticsVO = new StatisticsVO();

        log.info(item.getStudent_favorite_subject());

        statisticsVO.setFavorite_subject(item.getStudent_favorite_subject());

        return statisticsVO;
    }
}
