# Batch_Project

## 프로젝트 정보
***
### 버전 정보
* gradle-8.1.1
* Java 11
* Spring Boot 2.7.13
***
### 사용 의존성
* Lombok
* Mybatis 2.3.1
* Spring Batch
* MySql
***
> 해당 프로젝트에서는 tasklet 기반이 아닌 processor 기반으로 작성 했습니다. 대량의 데이터를 처리하기에는 processeor를 사용하는 것이 좋습니다.  
> tasklet은 단순하게 처리할 수 있는 장점이 있지만 대 용량을 감당하기엔 부하를 감당할 수 없습니다.
***
### Scheduler
##### yml 설정
![cron yml 설정](https://github.com/B-joon/Batch_Project/assets/75296934/7ea73395-2497-478f-bbc5-d793ae56fb49)
* 추가 Scheduler를 등록 시 주석을 참고하여 task2 방식으로 추가하면 된다.
##### @Scheduler 옵션
![Scheduler](https://github.com/B-joon/Batch_Project/assets/75296934/12a0e8ea-cfd3-48b8-8bc1-ec544ff0fdac)
##### CRON 표현식
![scheduler cron 표현식](https://github.com/B-joon/Batch_Project/assets/75296934/d37ca474-ddf2-433b-a5c1-190f08b26ef8)  
<pre><code>* : 모든 조건(All)을 의미 한다.  
? : 설정 값 없을 때(어떠한 값이든 상관 없다.) 단, 날짜와 요일 에서만 사용 가능.  
-(하이픈) : 범위 값을 지정할 때 사용 한다.  
,(콤마) : 여러 값을 지정할 때 사용 한다.  
/(슬러시) : 초기값과 증가치를 설정할 때 사용 한다.  
L : 마지막 - 지정할 수 있는 범위의 마지막 값 설정할 때 사용 한다. 단, 날짜와 요일 에서만 사용 가능.  
W : 가장 가까운 평일을 찾는다. 단, 일 에서만 사용 가능.</code></pre>  
(참고 : https://itworldyo.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8A%A4%EC%BC%80%EC%A4%84-%EC%84%A4%EC%A0%95-%EB%B2%95-Cron-%EC%A3%BC%EA%B8%B0%EC%84%A4%EC%A0%95)
***
### Spring Batch
Spring Batch는 로깅/추적, 트랜잭션 관리, 작업 처리 통계, 작업 재시작, 건너뛰기, 리소스 관리 등 대용량 레코드 처리에 필수적인 기능을 제공

![Transaction in Spring Batch](https://github.com/B-joon/Batch_Project/assets/75296934/d306c3f8-a54e-4ff9-8a31-146385c3269c)
(Transaction in Spring Batch)

##### MyBatisCursorItemReader< I > (I = Input type)
![reader](https://github.com/B-joon/Batch_Project/assets/75296934/7b755445-e37d-48c2-a4fb-271eb36c880f)
* ex) setQueryId("com.example.mapper.GetMapper.getAll") 여기서 getAll은 Mapper.xml 쿼리 ID 이다.

##### ItemProcessor<I, O> (I = Input type, O = output type)
![processor](https://github.com/B-joon/Batch_Project/assets/75296934/ed4e6454-55bc-4e31-9255-de8ee2556816)

###### MakeStatisticsItemProcessor()
![ItemProcessor](https://github.com/B-joon/Batch_Project/assets/75296934/f88d4fa6-7faf-42a8-892c-d0fe0577aa9b)

##### MyBatisBatchItemWriter< O > (O = output type)
![writer](https://github.com/B-joon/Batch_Project/assets/75296934/05da2c00-eeb7-4bcc-83c7-04a3882a758a)
* ex) setStatementId("com.example.mapper.ResultMapper.save") 여기서 save는 Mapper.xml 쿼리 ID 이다.

MakeStatisticsItemProcessor 를 사용 하지 않고 ItemProcessor 사진의 주석 처럼 람다 방식을 사용 가능.