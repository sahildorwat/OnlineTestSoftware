/* Students */
insert into students (id,lvl,name,user_id,password) values (1,'U','Tom Regan','tregan','tregan');
insert into students (id,lvl,name,user_id,password) values (2,'G','Jenelle Mick','jmick','jmick');
insert into students (id,lvl,name,user_id,password) values (3,'U','Michal Fisher','mfisher','mfisher');
insert into students (id,lvl,name,user_id,password) values (4,'U','Joseph Anderson','jander','jander');
insert into students (id,lvl,name,user_id,password) values (5,'G','Jitendra Harlalka','jharla','jharla');
insert into students (id,lvl,name,user_id,password) values (6,'G','Aishwarya Neelakantan','aneela','aneela');
insert into students (id,lvl,name,user_id,password) values (7,'G','Mary Jones','mjones','mjones');
insert into students (id,lvl,name,user_id,password) values (8,'G','James Moyer','jmoyer','jmoyer');

/* Professors */
insert into professors values(1, 'Kemafor Ogan', 'kogan', 'kogan');
insert into professors values(2, 'Rada Chirkova', 'rchirkova', 'rchirkova');
insert into professors values(3, 'Christipher Healey’, ‘chealey', 'chealey');

/* Scoring_policies */
insert into scoring_policies values(1, 'Latest Attempt');
insert into scoring_policies values(2, 'Maximum Score');
insert into scoring_policies values(3, 'Average Score');


/* Parameters; */
alter table parameters add parameter_name varchar(50);
alter table parameters add question_id Integer;
alter table parameters add foreign key (question_id) references questions(id);

/* courses_to_topics; */
insert into courses_to_topics values ('CSC440',4);
insert into courses_to_topics values ('CSC440',11);
insert into courses_to_topics values ('CSC540',12);
insert into courses_to_topics values ('CSC540',3);
insert into courses_to_topics values ('CSC540',4);
insert into courses_to_topics values ('CSC540',11);
insert into courses_to_topics values ('CSC541',4);
insert into courses_to_topics values ('CSC541',11);

/* attempts; */
insert into attempts values (1, 1, 5, null, null);
insert into attempts values (2, 2, 9, null, null);
insert into attempts values (3, 1, 9, null, null);

/* Topics */
INSERT INTO Topics VALUES(01, 'ER Model');
INSERT INTO Topics VALUES(02, 'SQL');
INSERT INTO Topics VALUES(03, 'Storing Data:Disks and Files');
INSERT INTO Topics VALUES(04, 'Primary File Organization');
INSERT INTO Topics VALUES(05, 'Hashing Techniques');
INSERT INTO Topics VALUES(06, 'Binary Tree Structures');
INSERT INTO Topics VALUES(07, 'AVL Trees');
INSERT INTO Topics VALUES(08, 'Sequential File Organization');
INSERT INTO Topics VALUES(09, 'BinarySearch');
INSERT INTO Topics VALUES(10, 'Interpolation Search');
INSERT INTO Topics VALUES(11, 'Tree Structures');
INSERT INTO Topics VALUES(12, 'Introduction to Database Design');


/* Courses */
INSERT INTO Courses VALUES('CSC440', 'Database Systems');
INSERT INTO Courses VALUES('CSC540', 'Database Systems');
INSERT INTO Courses VALUES('CSC541', 'Advanced Data Structures');

/* Exercise */
INSERT INTO Exercises VALUES(1, 'Homework 1', TIMESTAMP '2017-08-12 0:00:00', TIMESTAMP '2017-09-19 0:00:00', 3, 1, 3, 1, 2);
INSERT INTO Exercises VALUES(2, 'Homework 2', TIMESTAMP '2017-09-21 0:00:00', TIMESTAMP '2017-10-10 0:00:00', 3, 1, 4, 3, 1);
INSERT INTO Exercises VALUES(3, 'Homework 3', TIMESTAMP '2017-10-12 0:00:00', TIMESTAMP '2017-10-30 0:00:00', 3, 0, 4, 3, -1);


/* Exercise_Mapping */
INSERT INTO Exercise_Mapping VALUES(1, 'CSC540', 12);
INSERT INTO Exercise_Mapping VALUES(2, 'CSC540', 12);
INSERT INTO Exercise_Mapping VALUES(3, 'CSC540', 3);


/* Student_attempts_exercises */
select count(*) as cnt from student_attempts_exercises s where s.student_id = 2 and s.exercise_id = 1;

select s.exercise_id, s.student_id, s.attempt_id, a.attempt_no, a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id order by s.exercise_id, s.student_id, s.attempt_id;

select a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id and s.student_id = 6 and exercise_id = 2 order by a.attempt_no;

select scoring_policy_id from exercises where id = 2;

insert into student_attempts_exercises values(7, 1, 1);
insert into student_attempts_exercises values(7, 2, 1);
insert into student_attempts_exercises values(2, 3, 1);
insert into student_attempts_exercises values(6, 4, 2);
insert into student_attempts_exercises values(6, 5, 2);
insert into student_attempts_exercises values(7, 6, 2);
insert into student_attempts_exercises values(7, 7, 2);
insert into student_attempts_exercises values(6, 8, 3);
insert into student_attempts_exercises values(6, 9, 3);
insert into student_attempts_exercises values(6, 10, 3);
insert into student_attempts_exercises values(7, 11, 3);
insert into student_attempts_exercises values(7, 12, 3);

update exercise_questions set question_id = 1 ,exercise_id = 1, parameter_id = NULL where id = 1;
update exercise_questions set question_id = 2 ,exercise_id = 1, parameter_id = NULL where id = 2;
update exercise_questions set question_id = 3 ,exercise_id = 1, parameter_id = 1 where id = 3;
update exercise_questions set question_id = 1 ,exercise_id = 3, parameter_id = NULL where id = 4;
update exercise_questions set question_id = 2 ,exercise_id = 3, parameter_id = NULL where id = 5;
update exercise_questions set question_id = 3 ,exercise_id = 3, parameter_id = 2 where id = 6;


insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(1, '1', 1,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(1, '1', 2,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(1, '4', 3,'{ 0=1, 1=4, 2=5, 3=6 }', 1);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(2, '1', 1,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(2, '1', 2,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(2, '1', 3,'{ 0=1, 1=4, 2=5, 3=6 }', 1);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(3, '1', 1,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(3, '1', 3,'{ 0=1, 1=4, 2=5, 3=6 }', 1);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(8, '1', 4,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(8, '1', 5,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(8, '5', 6,'{ 0=1, 1=4, 2=5, 3=6 }', 2);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(9, '1', 4,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(9, '4', 5,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(9, '5', 6,'{ 0=1, 1=4, 2=5, 3=6 }', 2);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(10, '1', 4,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(10, '1', 5,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(10, '1', 6,'{ 0=1, 1=4, 2=5, 3=6 }', 2);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(11, '1', 4,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(11, '1', 5,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(11, '6', 6,'{ 0=1, 1=4, 2=5, 3=6 }', 2);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(12, '1', 4,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(12, '1', 5,'{ 0=1, 1=4, 2=5, 3=6 }', NULL);
insert into exercise_question_set(attempt_id, selected_ans,eq_id,options,parameter_id) values(12, '1', 6,'{ 0=1, 1=4, 2=5, 3=6 }', 2);



select ans.answer_text 
from answers ans, answer_set ans_set, exercise_question_set eqs, exercise_questions eq
where ans_set.question_id=1 
	and ans.answer_set_id = ans_set.id 
	and ans.is_correct = 1
	and eqs.attempt_id = 8
	and (ans_set.parameter_id = eqs.parameter_id OR (ans_set.parameter_id is NULL and eqs.parameter_id is NULL)) and eq.question_id = 1 and eq.exercise_id = 3 and eq.id = eqs.eq_id;

select ans.answer_text from answers ans, answer_set ans_set, exercise_question_set eqs where ans_set.question_id = 3 and (ans_set.parameter_id = eqs.parameter_id or ans_set.parameter_id = NULL) and eqs.attempt_id = 8 and ans.answer_set_id = ans_set.id and ans.is_correct = 1;

select ans.answer_text from answers ans, answer_set ans_set, exercise_question_set eqs where ans_set.question_id = 1 and ans_set.parameter_id is NULL and eqs.attempt_id = 8 and ans.answer_set_id = ans_set.id and ans.is_correct = 1;

select count(*) as cnt from student_attempts_exercises s where s.student_id = 2 and s.exercise_id = 1;

select s.exercise_id, s.student_id, s.attempt_id, a.attempt_no, a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id order by s.exercise_id, s.student_id, s.attempt_id;

select a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id and s.student_id = 6 and exercise_id = 2 order by a.attempt_no;

select scoring_policy_id from exercises where id = 2;
