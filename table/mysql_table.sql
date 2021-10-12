CREATE TABLE test01(
	tno INT,
    title VARCHAR(50)
);
test01
CREATE TABLE members(
	mno 	INTEGER NOT NULL COMMENT '회원일련번호',
    email 	VARCHAR(40) NOT NULL COMMENT '이메일',
    pwd		VARCHAR(100) NOT NULL COMMENT '암호',
    mname	VARCHAR(50) NOT NULL COMMENT '이름',
    cre_date DATETIME NOT NULL COMMENT '가입일',
    mod_date DATETIME NOT NULL COMMENT '마지막암호변경일'
)
COMMENT '회원기본정보';

ALTER TABLE members
	ADD CONSTRAINT members_pk_mno 
		PRIMARY KEY(mno);
        
CREATE UNIQUE INDEX members_uix_email
	ON members(email asc);

-- 시퀀스 대신 사용, 자동으로 1씩 증가
ALTER TABLE members
	MODIFY COLUMN mno INTEGER NOT NULL AUTO_INCREMENT
    COMMENT '회원일련번호';
    
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s1@test.com','1111','아이유',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s2@test.com','1111','트와이스사나',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s3@test.com','1111','블랙핑크제니',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s4@test.com','1111','차은우',NOW(),NOW());
INSERT INTO members(email,pwd,mname,cre_date,mod_date)
VALUES ('s5@test.com','1111','박보검',NOW(),NOW());

COMMIT;

SELECT * FROM members;












