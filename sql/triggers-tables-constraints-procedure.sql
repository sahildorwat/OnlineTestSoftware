/*Create Table Queries:*/
/* exercise_question_set */
create table exercise_question_set (attempt_id integer, selected_answer integer,question_id integer,foreign key(attempt_id) references attempts, foreign key(question_id) references questions);

/* attempts */
create table attempts(id integer,attempt_no integer,score DECIMAL(5,2),start_time timestamp, end_time timestamp, primary key(id));

/* questions */
create table questions(id integer, actual_text varchar(300), detailed_explanation varchar(1024), hint varchar(100), difficulty_level integer, topic_id integer, primary key(id), foreign key(topic_id) references topics);

/* answer_set */
create table answer_set(id integer, question_id integer, parameter_id integer, primary key(id), foreign key(question_id) references questions, foreign key(parameter_id) references parameters);

/* answers */
create table answers(id integer, is_correct integer, answer_text varchar(1024), answer_set_id integer, primary key(answer_set_id, id), foreign key(answer_set_id) references answer_set);

/* student_attempts_exercises */
create table student_attempts_exercises(student_id integer, attempt_id integer, exercise_id integer, foreign key(student_id) references students, foreign key(attempt_id) references attempts, foreign key(exercise_id) references exercises);

/* courses */
create table courses ( id varchar(6), name varchar(50), PRIMARY KEY(id));

/* topics */
create table topics (id integer, name varchar(50), PRIMARY KEY(id));

/* course_to_topics (mapping table) */
create table courses_to_topics( course_id varchar(6), topic_id Integer, PRIMARY KEY(course_id,topic_id), FOREIGN KEY(course_id) references courses, FOREIGN KEY(topic_id) references topics ON DELETE CASCADE);

/* parameters */
create table parameters (id Integer, vals varchar(50), PRIMARY KEY(id));

/* scoring_policies */
create table scoring_policies (id Integer, vals varchar(50), PRIMARY KEY(id));

/* students */
create table students (id Integer, lvl varchar(1), PRIMARY KEY(id));
-->alter table students add name varchar(50) ;
-->alter table students add user_id varchar(50) ;
-->alter table students add password varchar(50) ;
-->create table students (id Integer, name varchar(50),user_id varchar(50),password varchar(50),lvl varchar(1), PRIMARY KEY(id));
create table professors (id Integer, name varchar(50), PRIMARY KEY(id));
create table enrollment( course_id varchar(6), student_id Integer, PRIMARY KEY(course_id,student_id), FOREIGN KEY(course_id) references courses, FOREIGN KEY(student_id) references students ON DELETE CASCADE);

/* teaches */
create table teaches( professor_id Integer, course_id varchar(6), start_date timestamp, end_date timestamp, PRIMARY KEY(course_id, professor_id), FOREIGN KEY(course_id) references courses, FOREIGN KEY(professor_id) references professors ON DELETE CASCADE);

/* courses_to _ta */
create table courses_to_ta( professor_id Integer, course_id varchar(6), ta_id Integer, PRIMARY KEY(course_id, professor_id, ta_id), FOREIGN KEY(ta_id) references students, FOREIGN KEY(course_id) references courses, FOREIGN KEY(professor_id) references professors ON DELETE CASCADE);

/* exercises */
create table exercises ( id Integer, name varchar(50), start_time timestamp, end_time timestamp, total_questions integer, penalty_per_question decimal(5,2), points_per_question decimal(5,2), scoring_policy_id integer, num_of_retries integer, PRIMARY KEY(id), FOREIGN KEY (scoring_policy_id) references scoring_policies);   

/* exercise_mapping */
CREATE TABLE Exercise_Mapping (exercise_id integer, course_id varchar(6), topic_id integer, FOREIGN KEY(exercise_id) REFERENCES Exercises(id), FOREIGN KEY(course_id) REFERENCES Courses(id), FOREIGN KEY(topic_id) REFERENCES Topics(id));

select top 10 percent a.answer_text from answers a, answer_set ast where ast.question_id = 1 and a.answer_set_id = ast.id and a.is_correct=0 order by newid();

select top 10 percent a.answer_text from answers a order by newid();


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


/* Constraints: */
Alter table questions add constraints diff_level check (difficulty_level in (1,2,3));
Alter table students add constraints s_lvl check (lvl in (‘U’, ‘G’));
Alter table attempts add constraints time_test check (start_time < end_time);
Alter table teaches add constraints teach_test check (start_date < end_date);
Alter table exercises add constraints t_test check (start_time <= end_time);
Alter table exercises add constraints retries_test check (num_of_retries >= -1 and num_of_retries <> 0);



/* Triggers/Procedures: */
CREATE TRIGGER delete_student_g AFTER DELETE on enrollment
FOR EACH ROW
BEGIN
DELETE FROM student_attempts_exercsies
    WHERE enrollment.student_id = student_attempts_exercsies.student_id;
END;
/

CREATE TRIGGER delete_student_data AFTER DELETE on student_attempts_exercises
FOR EACH ROW
BEGIN
DELETE FROM attempts
    WHERE attempts.id = student_attempts_exercsies.attempt_id;
END;
/


