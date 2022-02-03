insert into users_with_roles(username,password,enabled, role)
	values('admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 'ROLE_ADMIN'); -- admin@123
insert into users_with_roles(username,password,enabled, role)
	values('student0','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 'ROLE_STUDENT'); -- admin@123
insert into users_with_roles(username,password,enabled, role)
	values('tutor0','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',1, 'ROLE_TUTOR'); -- admin@123

select * from users_with_roles;
