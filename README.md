# Batch_Project

## 프로젝트 정보
### 버전 정보
* gradle-8.1.1
* Java 11
* Spring Boot 2.7.13

### 사용 의존성
* Lombok
* Mybatis 2.3.1
* Spring Batch
* MySql

##### Scheduler CRON 표현식
![scheduler cron 표현식](https://github.com/B-joon/Batch_Project/assets/75296934/d37ca474-ddf2-433b-a5c1-190f08b26ef8)
<code>* : 모든 조건(All)을 의미 한다.  
? : 설정 값 없을 때(어떠한 값이든 상관 없다.) 단, 날짜와 요일 에서만 사용 가능.  
-(하이픈) : 범위 값을 지정할 때 사용 한다.  
,(콤마) : 여러 값을 지정할 때 사용 한다.  
/(슬러시) : 초기값과 증가치를 설정할 때 사용 한다.  
L : 마지막 - 지정할 수 있는 범위의 마지막 값 설정할 때 사용 한다. 단, 날짜와 요일 에서만 사용 가능.  
W : 가장 가까운 평일을 찾는다. 단, 일 에서만 사용 가능.</code>  
(참고 : https://itworldyo.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8A%A4%EC%BC%80%EC%A4%84-%EC%84%A4%EC%A0%95-%EB%B2%95-Cron-%EC%A3%BC%EA%B8%B0%EC%84%A4%EC%A0%95)
