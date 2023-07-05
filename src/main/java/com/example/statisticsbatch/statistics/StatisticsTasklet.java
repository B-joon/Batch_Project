package com.example.statisticsbatch.statistics;

import com.example.statisticsbatch.persistence.mapper.TestMapper;
import com.example.statisticsbatch.persistence.mapper.batch.TestBatchMapper;
import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StatisticsTasklet implements Tasklet {

    private final TestMapper studentMapper;
    private final TestBatchMapper statisticsMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 학생 데이터 호출 및 통계 생성 로직
        List<StudentVO> students = studentMapper.getAllStudents();

        for (StudentVO student : students) {
            String favoriteSubject = student.getStudent_favorite_subject();

            StatisticsVO statistics = new StatisticsVO();
            statistics.setFavorite_subject(favoriteSubject);

            statisticsMapper.insertStatistics(statistics);
        }

        return RepeatStatus.FINISHED;
    }
}
