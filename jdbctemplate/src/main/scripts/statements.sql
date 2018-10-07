CREATE TABLE artist (
  id bigint NOT NULL,
  firstname varchar(250) not null,
  lastname varchar(250) not null,
  hotscore int not null,
  PRIMARY KEY( id )
  )


  drop table artist

  select * from artist

  truncate table artist


CREATE TABLE video (
  id bigint NOT NULL,
  publication_date date,
  name varchar(250) not null,
  PRIMARY KEY( id )
  )


CREATE TABLE award (
  id bigint NOT NULL,
  name varchar(250) not null,
  PRIMARY KEY( id )
  )