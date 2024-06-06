CREATE TABLE CUSTOMER
(
    ID        BIGINT(20) NOT NULL AUTO_INCREMENT,
    FULL_NAME VARCHAR(45)  DEFAULT NULL,
    EMAIL     VARCHAR(100) NOT NULL,
    ADDRESS   VARCHAR(100) DEFAULT NULL,
    USERNAME  VARCHAR(45)  NOT NULL,
    PASSWORD  VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE KEY CUSTOMER_UK_1 (USERNAME)
) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci;