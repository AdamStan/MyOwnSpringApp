CREATE DATABASE DeanOffice;
GO
USE DeanOffice
GO
create table users(
	username nvarchar(50) not null primary key,
	[password] nvarchar(100) not null,
	[enabled] BIT not null
);
create table authorities (
	username nvarchar(50) not null,
	authority nvarchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into users(username,password,enabled)
	values('admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1); -- admin@123
insert into authorities(username,authority) 
	values('admin','ROLE_ADMIN');

insert into users(username,[password], [enabled])
	values('student0', '$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1);
insert into authorities(username,authority) 
	values('student0','ROLE_STUDENT');
insert into users(username,[password], [enabled])
	values('tutor0', '$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1);
insert into authorities(username,authority) 
	values('tutor0','ROLE_TUTOR');

select * from dbo.users

select * from dbo.authorities