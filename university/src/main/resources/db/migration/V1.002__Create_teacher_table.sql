create sequence teacher_seq start with 1 increment by 50;
create table teacher (birthdate date, id integer not null, name varchar(255), primary key (id));