
DROP TABLE IF EXISTS DIRANDFILE;
DROP TABLE IF EXISTS HIERARHIFILES;


--Таблица директорий
CREATE TABLE IF NOT EXISTS DIRANDFILE (
  IDDIRANDFILE INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  CREATED DATETIME  NULL,
  PATH VARCHAR(1000) NULL,
  DIRCNT INT,
  FILECNT INT,
  SUMMURYSIZE VARCHAR(10) NULL,
  PRIMARY KEY (IDDIRANDFILE)
) ;

--Таблица файлов
CREATE TABLE IF NOT EXISTS HIERARHIFILES (
  IDTEST INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  SIZE VARCHAR(10) NULL,
  NAME VARCHAR(1000) NULL,
  OWNERID INT,
  PRIMARY KEY (IDTEST)
) ;