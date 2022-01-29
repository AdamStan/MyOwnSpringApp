insert into dbo.roles values(1, 'ROLE_ADMIN');
insert into dbo.roles values(2, 'ROLE_TUTOR');
insert into dbo.roles values(3, 'ROLE_STUDENT');


insert into users2(username,password,enabled, role_id)
	values('admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 1); -- admin@123
insert into users2(username,password,enabled, role_id)
	values('student0','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 3); -- admin@123
insert into users2(username,password,enabled, role_id)
	values('tutor0','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 2); -- admin@123

select * from users2;
select * from roles;
