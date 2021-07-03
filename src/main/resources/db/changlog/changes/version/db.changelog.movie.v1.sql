-- liquibase formatted sql

-- changeset superops:1624695102262-1
CREATE TABLE "booking" ("id" UUID NOT NULL, "booked_seats" BIGINT, "booking_time" TIMESTAMP WITHOUT TIME ZONE, "show_id" UUID, "user_id" UUID, CONSTRAINT "booking_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-2
CREATE TABLE "shows" ("id" UUID NOT NULL, "show_time" TIMESTAMP WITHOUT TIME ZONE, "movie_id" UUID, "screen_id" UUID, CONSTRAINT "shows_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-3
CREATE TABLE "movie" ("id" UUID NOT NULL, "duration" FLOAT8, "language" VARCHAR, "plot" VARCHAR, "poster_url" VARCHAR, "release_date" date, "title" VARCHAR, CONSTRAINT "movie_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-4
CREATE TABLE "users" ("id" UUID NOT NULL, "email_id" VARCHAR, "name" VARCHAR, "password" VARCHAR, "username" VARCHAR, CONSTRAINT "users_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-5
CREATE TABLE "auditorium" ("id" UUID NOT NULL, "name" VARCHAR, "screen_number" BIGINT, "total_seats" BIGINT, "screen_id" UUID, CONSTRAINT "screen_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-6
CREATE TABLE "theatre" ("id" UUID NOT NULL, "name" VARCHAR, CONSTRAINT "theatre_pkey" PRIMARY KEY ("id"));

-- changeset superops:1624695102262-7
ALTER TABLE "booking" ADD CONSTRAINT "fk3hfymurc2960dg7d793rr0qcr" FOREIGN KEY ("show_id") REFERENCES "shows" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset superops:1624695102262-8
ALTER TABLE "booking" ADD CONSTRAINT "fk7udbel7q86k041591kj6lfmvw" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset superops:1624695102262-9
ALTER TABLE "shows" ADD CONSTRAINT "fk4qskmne5ijrtsqlxym3c70nha" FOREIGN KEY ("movie_id") REFERENCES "movie" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset superops:1624695102262-10
ALTER TABLE "shows" ADD CONSTRAINT "fkqy9162jhrejk4hqejebdp6j91" FOREIGN KEY ("screen_id") REFERENCES "auditorium" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset superops:1624695102262-11
ALTER TABLE "auditorium" ADD CONSTRAINT "fkd7ppv7wu407o182qkjti7gu8p" FOREIGN KEY ("screen_id") REFERENCES "theatre" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

