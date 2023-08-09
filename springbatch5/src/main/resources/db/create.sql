drop table follower;
drop table statisticsGender;
drop table statisticsAge;

drop table BATCH_JOB_SEQ;
drop table BATCH_JOB_EXECUTION_SEQ;
drop table BATCH_JOB_EXECUTION_CONTEXT;
drop table BATCH_JOB_EXECUTION_PARAMS;
drop table BATCH_STEP_EXECUTION_SEQ;
drop table BATCH_STEP_EXECUTION_CONTEXT;
drop table BATCH_STEP_EXECUTION;
drop table BATCH_JOB_EXECUTION;
drop table BATCH_JOB_INSTANCE;

CREATE TABLE BATCH_JOB_INSTANCE  (
    JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT ,
    JOB_NAME VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32) NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
    JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT  ,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME DATETIME(6) NOT NULL,
    START_TIME DATETIME(6) DEFAULT NULL ,
    END_TIME DATETIME(6) DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED DATETIME(6),
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
        references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    PARAMETER_NAME VARCHAR(100) NOT NULL ,
    PARAMETER_TYPE VARCHAR(100) NOT NULL ,
    PARAMETER_VALUE VARCHAR(2500) ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
     STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
     VERSION BIGINT NOT NULL,
     STEP_NAME VARCHAR(100) NOT NULL,
     JOB_EXECUTION_ID BIGINT NOT NULL,
     CREATE_TIME DATETIME(6) NOT NULL,
     START_TIME DATETIME(6) DEFAULT NULL ,
     END_TIME DATETIME(6) DEFAULT NULL ,
     STATUS VARCHAR(10) ,
     COMMIT_COUNT BIGINT ,
     READ_COUNT BIGINT ,
     FILTER_COUNT BIGINT ,
     WRITE_COUNT BIGINT ,
     READ_SKIP_COUNT BIGINT ,
     WRITE_SKIP_COUNT BIGINT ,
     PROCESS_SKIP_COUNT BIGINT ,
     ROLLBACK_COUNT BIGINT ,
     EXIT_CODE VARCHAR(2500) ,
     EXIT_MESSAGE VARCHAR(2500) ,
     LAST_UPDATED DATETIME(6),
     constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
         references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
    STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
        references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
    JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ START WITH 1 MINVALUE 1 MAXVALUE 9223372036854775806 INCREMENT BY 1 NOCACHE NOCYCLE ENGINE=InnoDB;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ START WITH 1 MINVALUE 1 MAXVALUE 9223372036854775806 INCREMENT BY 1 NOCACHE NOCYCLE ENGINE=InnoDB;
CREATE SEQUENCE BATCH_JOB_SEQ START WITH 1 MINVALUE 1 MAXVALUE 9223372036854775806 INCREMENT BY 1 NOCACHE NOCYCLE ENGINE=InnoDB;

create table follower (
    seq int primary key auto_increment,
    id varchar(50) unique not null,
    name varchar(50) not null,
    gender varchar(10) not null,
    age int not null,
    create_at datetime not null,
    modified_at datetime
);

create table statisticsGender (
    idx bigint primary key auto_increment,
    gender varchar(10) not null,
    count int not null,
    create_at datetime not null default current_timestamp
);

create table statisticsAge (
    idx bigint primary key auto_increment,
    age int not null,
    count int not null,
    create_at datetime not null default current_timestamp
);