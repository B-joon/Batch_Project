package com.example.statisticsbatch.batch.statistics.mapper;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;

import java.util.List;

public interface StatisticsMapper {

    List<StatisticsVO> findAll();

    StatisticsVO getStatisticsByFavoriteSubject(String favorite_subject);

    int updateTest(StatisticsVO statisticsVO);

}
