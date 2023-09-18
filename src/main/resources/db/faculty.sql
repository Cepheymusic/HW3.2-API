--liquibase formatted sql

--changeset Cephey:2
CREATE INDEX faculty_name_color_index ON faculty (name, color);