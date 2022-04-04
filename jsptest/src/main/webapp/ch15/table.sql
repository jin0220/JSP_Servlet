DROP SEQUENCE   seq_tblBoard_no;
CREATE SEQUENCE seq_tblBoard_no
INCREMENT BY 1 
START WITH 1 ;

SELECT seq_tblBoard_no.currval FROM dual;

DROP TABLE TBLBOARD;
CREATE TABLE TBLBOARD
 (  NUM NUMBER(11,0), 
  NAME VARCHAR2(40), 
  SUBJECT VARCHAR2(100), 
  CONTENT VARCHAR2(500), 
  POS NUMBER(7,0), 
  REF NUMBER(7,0), 
  DEPTH NUMBER(7,0), 
  REGDATE DATE, 
  PASS VARCHAR2(30), 
  IP VARCHAR2(30), 
  COUNT NUMBER(7,0), 
  FILENAME VARCHAR2(500), 
  FILESIZE NUMBER(11,0), 
   PRIMARY KEY (NUM)
  ) ;
SELECT * FROM TBLBOARD;

insert INTO TBLBOARD(num, name,content,subject,ref,pos,depth,regdate,pass,count,ip,filename,filesize)
values(seq_tblboard_no.nextval ,'aaa', 'bbb', 'ccc', 0, 0, 0, sysdate, '1111',0, '127.0.0.1', null, 0);