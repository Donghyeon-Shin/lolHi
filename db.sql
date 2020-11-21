# 데이터 베이스 삭제
DROP DATABASE IF EXISTS lolHi;

# 데이터 베이스 생성
CREATE DATABASE lolHi;

USE lolHi;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    memberId INT(10) NOT NULL,
    boardId INT(10) NOT NULL,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    hit INT(10) UNSIGNED DEFAULT 0 NOT NULL
);

# 게시물 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 1,
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
boardId = 2,
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 2,
title = '제목3',
`body` = '내용3';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
boardId = 1,
title = '제목4',
`body` = '내용4';

# member 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    email CHAR(100) NOT NULL,
    `name` CHAR(10) NOT NULL
);

# member 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'sbs123',
loginPw = 'sbs123414',
email = 'sbs123@gmail.com',
`name` = 'admin'; 

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = '1234',
loginPw = '1234',
email = '1234@gmail.com',
`name` = '숫자'; 

# 댓글 테이블 생성
CREATE TABLE reply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 ID
    relTypeCode CHAR(50) NOT NULL # 관련 데이터 타입
);

# 댓글 데이터 생성
INSERT INTO reply
SET regDate = NOW(),
memberId = 1,
relId = 1,
relTypeCode = 'article',
`body` = '재밌네요';

# board 테이블 생성
CREATE TABLE board(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `code` CHAR(50) NOT NULL
);

# code 인덱스 추가
ALTER TABLE board ADD INDEX(`code`);

# board 데이터 생성 
INSERT INTO board
SET regDate = NOW(),
`name` = '공지사항',
`code` = 'notice';

INSERT INTO board
SET regDate = NOW(),
`name` = '자유게시판',
`code` = 'free';

#현재 회원 비밀번호 암호화
UPDATE `member`
SET loginPw = SHA2(loginPw, 256);

# 부가정보테이블 
# 댓글 테이블 추가
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL,
    `relId` INT(10) UNSIGNED NOT NULL,
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(30) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
## 변수찾는 속도 최적화
# 기존 인덱스 삭제후 유니크로 변경, 왜냐하면 attr의 특정 조합은 유니크여야 하기 때문에
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`); 

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;


