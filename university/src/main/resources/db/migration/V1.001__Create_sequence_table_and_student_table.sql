create sequence student_seq start with 1 increment by 50;
create table student (birthdate date, id integer not null, semester integer not null, name varchar(255), primary key (id));
