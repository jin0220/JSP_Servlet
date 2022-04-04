DROP TABLE tblPollList;

CREATE TABLE tblPollList (
  num number(6), 
  question varchar2(1000) DEFAULT '' NOT NULL ,
  sdate date ,
  edate date ,
  wdate date ,
  type number(6) DEFAULT 1 NOT NULL ,
  active number(6) DEFAULT 1,
  PRIMARY KEY (num)
);

CREATE SEQUENCE seq_tblPollList_num
INCREMENT BY 1 
START WITH 1 ;



DROP TABLE tblPollItem;

CREATE TABLE tblPollItem (
  listnum number(6) NOT NULL,
  itemnum number(6)  DEFAULT 0 NOT NULL,
  item varchar2(200) DEFAULT '' NOT NULL,
  count number(6) DEFAULT 0 NOT NULL,
  PRIMARY KEY (listnum,itemnum)
); 

COMMIT;