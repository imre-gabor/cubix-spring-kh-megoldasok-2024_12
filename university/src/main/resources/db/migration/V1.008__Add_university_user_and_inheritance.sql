alter table student drop column name, drop column birthdate;
alter table teacher drop column name, drop column birthdate;

create sequence university_user_seq start with 1 increment by 50;
create table university_user (id int4 not null, birthdate date, name varchar(255), password varchar(255), username varchar(255), primary key (id));
create table university_user_aud (id int4 not null, rev int4 not null, revtype int2, birthdate date, name varchar(255), password varchar(255), username varchar(255), primary key (id, rev));

alter table if exists student add constraint FK9wqqrdwh5j5tkja9ix5n35671 foreign key (id) references university_user;
alter table if exists student_aud add constraint FKct7l5aou36vau0j2ves7vd6r5 foreign key (id, rev) references university_user_aud;
alter table if exists teacher add constraint FK3ishnayracolicixocno05kmt foreign key (id) references university_user;
alter table if exists teacher_aud add constraint FKc7h10qomjgvthn1bnh32kv44f foreign key (id, rev) references university_user_aud;

alter table if exists university_user_aud add constraint FK3kui2v2p71dteg77ff3dwi2lo foreign key (rev) references revinfo;