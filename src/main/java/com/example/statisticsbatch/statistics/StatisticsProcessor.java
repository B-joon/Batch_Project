package com.example.statisticsbatch.statistics;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.springframework.batch.item.ItemProcessor;

public class StatisticsProcessor implements ItemProcessor<StudentVO, StatisticsVO> {

    @Override
    public StatisticsVO process(StudentVO student) throws Exception {
        StatisticsVO statistics = new StatisticsVO();
        statistics.setFavorite_subject(student.getStudent_favorite_subject());
        // 해당 과목의 투표 수 계산 및 설정
        return statistics;
    }

}
