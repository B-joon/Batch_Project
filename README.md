# Ubivelox Batch Template
***
### 개발환경
***
| spring batch             | spring batch 5          |
|:-------------------------|:------------------------|
| gradle-8.1.1             | gradle-8.1.1            |
| Java 11                  | Java 20                 |
| Spring Boot 2.7.13       | Spring Boot 3.1.2       |
| Lombok                   | Lombok                  |
| Mybatis 2.3.1            | Mybatis 3.0.2           |
| Spring Batch             | Spring Batch 5          |
| MariaDB                  | MariaDB                 |
***
### DB설정 및 테스트 순서
1. `/resources/application.yml` 파일을 수정하여 DB접속정보 설정
2. `/resources/db/create.sql` 에 모든 쿼리를 실행하여 테이블 삭제 및 생성.
    1. spring batch 5의 경우 자동으로 메타테이블이 생성 되지 않아 직접 생성해야 합니다.
3. `/resources/db/insert.sql` 을 실행하여 더미데이터 생성.
4. SpringbatchApplication 클래스에서 main을 실행하면 됩니다.
***
>Batch 코드 설명은 MakeGenderBatchJobConfig class 파일을 참고하시면 됩니다.
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