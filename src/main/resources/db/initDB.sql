DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS faculties;
DROP TABLE IF EXISTS classrooms;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1000;

CREATE TABLE classrooms
(
  id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  number   VARCHAR NOT NULL UNIQUE,
  location VARCHAR NOT NULL,
  capacity INTEGER NOT NULL
);
CREATE UNIQUE INDEX classrooms_unique_number_idx
  ON classrooms (number);

CREATE TABLE faculties
(
  id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name VARCHAR NOT NULL UNIQUE
);
CREATE UNIQUE INDEX faculties_unique_name_idx
  ON faculties (name);

CREATE TABLE groups
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL UNIQUE,
  faculty_id INTEGER NOT NULL,
  FOREIGN KEY (faculty_id) REFERENCES faculties (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX groups_unique_name_idx
  ON groups (name);

CREATE TABLE departments
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL UNIQUE,
  faculty_id INTEGER NOT NULL,
  FOREIGN KEY (faculty_id) REFERENCES faculties (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX departments_unique_name_idx
  ON departments (name);

CREATE TABLE teachers
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  firstname     VARCHAR NOT NULL,
  lastname      VARCHAR NOT NULL,
  salary        INTEGER NOT NULL,
  department_id INTEGER NOT NULL,
  FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE
);

CREATE TABLE students
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  firstname VARCHAR NOT NULL,
  lastname  VARCHAR NOT NULL,
  group_id  INTEGER NOT NULL,
  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE lessons
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  subject    VARCHAR NOT NULL,
  week_day   VARCHAR NOT NULL,
  day_time   VARCHAR NOT NULL,
  room_id    INTEGER NOT NULL,
  teacher_id INTEGER NOT NULL,
  group_id   INTEGER NOT NULL,
  FOREIGN KEY (room_id) REFERENCES classrooms (id) ON DELETE CASCADE,
  FOREIGN KEY (teacher_id) REFERENCES teachers (id) ON DELETE CASCADE,
  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX lessons_unique_day_time_room_idx
  ON lessons (week_day, day_time, room_id);
CREATE UNIQUE INDEX lessons_unique_day_time_teacher_idx
  ON lessons (week_day, day_time, teacher_id);
CREATE UNIQUE INDEX lessons_unique_day_time_group_idx
  ON lessons (week_day, day_time, group_id);


