-- 프로젝트 테이블 생성 SQL
create table projects(
 pno INTEGER NOT NULL COMMENT '프로젝트일련번호',
 pname VARCHAR(255) NOT NULL COMMENT '프로젝트명',
 content TEXT NOT NULL COMMENT '설명',
 sta_date DATETIME NOT NULL COMMENT '시작일',
 end_date DATETIME NOT NULL COMMENT '종료일',
 state INTEGER NOT NULL COMMENT '상태',
 cre_date DATETIME NOT NULL COMMENT '생성일',
 tags VARCHAR(255) NULL COMMENT '태그'
)
COMMENT '프로젝트';

-- pno를 pk로 정하자
ALTER TABLE projects ADD CONSTRAINT PK_PROJECTS
	PRIMARY KEY(pno);

-- pno컬럼에 대해 자동 증가 일련번호 기능 설정
ALTER TABLE projects MODIFY COLUMN
	pno INTEGER NOT NULL AUTO_INCREMENT COMMENT '프로젝트일련번호';

-- 프로젝트 멤버 테이블 생성
CREATE TABLE prj_membs(
 pno INTEGER NOT NULL COMMENT '프로젝트일련번호',
 mno INTEGER NOT NULL COMMENT '회원일련번호',
 level INTEGER NOT NULL COMMENT '등급',
 state INTEGER NOT NULL COMMENT '상태',
 mod_date DATETIME NOT NULL COMMENT '상태변경일'
)
COMMENT '프로젝트멤버';

-- prj_membs 테이블의 기본키 설정
ALTER TABLE prj_membs ADD CONSTRAINT PK_PRJ_MEMBS
	PRIMARY KEY(pno, mno);





