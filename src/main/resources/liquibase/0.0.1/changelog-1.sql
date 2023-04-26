--liquibase formatted sql

--changeset Swamptea:9
--comment first migration

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL,
    login character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
    );

Insert into users (id, login, password, role)
values(1, 'user1', '$2a$10$0y2q6yS8KWdCqH83MGVlGuLsQf8VYhMXWSfJUFt6Nm7BlUH9SFP2O', 'USER'),
      (2, 'user2', '$2a$10$pmbc/94Ub5R/ecrekB1Q3u8wRD8mccGjeW/JIo1Lbcy/IApb7dTWO', 'USER'),
      (3, 'admin', '$2a$10$Y5/68FPiJmoUGnBviQMmoeOuGpi7jmlJ9ntZNNiPBiPVovpGD6i4W', 'ADMIN');

CREATE TABLE IF NOT EXISTS public.file
(
    id bigint NOT NULL,
    filename character varying(255) COLLATE pg_catalog."default",
    my_file oid,
    size integer,
    type character varying(255) COLLATE pg_catalog."default",
    owner character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT file_pkey PRIMARY KEY (id)
);


