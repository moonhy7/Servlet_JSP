CREATE TABLE PROJECTS (
	PNO INTEGER NOT NULL COMMENT '프로젝트 일련번호',
    PNAME VARCHAR(225) NOT NULL COMMENT '프로젝트명',
    CONTENT TEXT NOT NULL COMMENT '설명',
    STA_DATE DATETIME NOT NULL COMMENT '시작일',
    END_DATE DATETIME NOT NULL COMMENT '종료일',
    STATE INTEGER NOT NULL COMMENT '상태',
    CRE_DATE DATETIME NOT NULL COMMENT '생성일',
    TAGS VARCHAR(225) NOT NULL COMMENT '태그'
)
COMMENT '프로젝트';

ALTER TABLE PROJECTS
ADD CONSTRAINT PK_PROJECTS
PRIMARY KEY (PNO);

ALTER TABLE PROJECTS
MODIFY COLUMN PNO INTEGER NOT NULL AUTO_INCREMENT COMMENT '프로젝트 일련번호';

CREATE TABLE PRJ_MEMBS (
	PNO INTEGER NOT NULL COMMENT '프로젝트 일련번호',
    MNO INTEGER NOT NULL COMMENT '회원 일련번호',
    LEVEL INTEGER NOT NULL COMMENT '등급',
    STATE INTEGER NOT NULL COMMENT '상태',
    MOD_DATE DATETIME NOT NULL COMMENT '상태변경일'
)
COMMENT '프로젝트멤버';

ALTER TABLE PRJ_MEMBS
ADD CONSTRAINT PK_PRJ_MEMBS
PRIMARY KEY(PNO, MNO);