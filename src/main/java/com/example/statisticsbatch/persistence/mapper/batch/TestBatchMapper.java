package com.example.statisticsbatch.persistence.mapper.batch;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestBatchMapper {

    List<StatisticsVO> getStatistics();
    void insertStatistics(StatisticsVO statistics);

}
