--liquibase formatted sql

--changeset Cephey:1
CREATE INDEX student_name_index ON student (name);