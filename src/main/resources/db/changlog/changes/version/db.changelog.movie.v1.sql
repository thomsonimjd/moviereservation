-- liquibase formatted sql

-- changeset thomson:1625297229509-1
CREATE TABLE "public"."booking" ("id" UUID NOT NULL, "booking_expire_time" TIMESTAMP WITHOUT TIME ZONE, "booking_time" TIMESTAMP WITHOUT TIME ZONE, "booking_type" VARCHAR(255), "payment_status" VARCHAR(255), "booking_status" VARCHAR(255), "show_id" UUID, "user_id" UUID, CONSTRAINT "booking_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-2
CREATE TABLE "public"."shows" ("id" UUID NOT NULL, "is_house_full" BOOLEAN, "end_time" TIMESTAMP WITHOUT TIME ZONE, "start_time" TIMESTAMP WITHOUT TIME ZONE, "audi_id" UUID, "movie_id" UUID, CONSTRAINT "shows_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-3
CREATE TABLE "public"."movie" ("id" UUID NOT NULL, "duration_min" INTEGER, "language" VARCHAR(255), "plot" VARCHAR(255), "poster_url" VARCHAR(255) NOT NULL, "release_date" date NOT NULL, "title" VARCHAR(255) NOT NULL, CONSTRAINT "movie_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-4
CREATE TABLE "public"."users" ("id" UUID NOT NULL, "email_id" VARCHAR(255), "name" VARCHAR(255), "password" VARCHAR(255), "user_type" INTEGER, "username" VARCHAR(255), CONSTRAINT "users_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-5
CREATE TABLE "public"."auditorium" ("id" UUID NOT NULL, "audi_number" BIGINT NOT NULL, "name" VARCHAR(255) NOT NULL, "theatre_id" UUID, CONSTRAINT "auditorium_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-6
CREATE TABLE "public"."theatre" ("id" UUID NOT NULL, "address" VARCHAR(255) NOT NULL, "name" VARCHAR(255) NOT NULL, "user_id" UUID, CONSTRAINT "theatre_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-7
CREATE TABLE "public"."seat_booked" ("id" UUID NOT NULL, "booking_id" UUID, "seat_id" UUID, CONSTRAINT "seat_booked_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-8
CREATE TABLE "public"."seats" ("id" UUID NOT NULL, "number" INTEGER NOT NULL, "row" VARCHAR(255) NOT NULL, "audi_id" UUID, CONSTRAINT "seats_pkey" PRIMARY KEY ("id"));

-- changeset thomson:1625297229509-9
ALTER TABLE "public"."booking" ADD CONSTRAINT "fk3hfymurc2960dg7d793rr0qcr" FOREIGN KEY ("show_id") REFERENCES "public"."shows" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-10
ALTER TABLE "public"."booking" ADD CONSTRAINT "fk7udbel7q86k041591kj6lfmvw" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-11
ALTER TABLE "public"."seat_booked" ADD CONSTRAINT "fkd1jo0mwkfb6ge8u0muaw7kxgd" FOREIGN KEY ("booking_id") REFERENCES "public"."booking" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-12
ALTER TABLE "public"."shows" ADD CONSTRAINT "fk4qskmne5ijrtsqlxym3c70nha" FOREIGN KEY ("movie_id") REFERENCES "public"."movie" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-13
ALTER TABLE "public"."shows" ADD CONSTRAINT "fkpx9pbe5ccwc7ug2ax0sxux5ak" FOREIGN KEY ("audi_id") REFERENCES "public"."auditorium" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-14
ALTER TABLE "public"."theatre" ADD CONSTRAINT "fkaqefg3guc182iwwat6jfemxdk" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-15
ALTER TABLE "public"."auditorium" ADD CONSTRAINT "fk7utg9cwqt56kfn88dpr3puw9u" FOREIGN KEY ("theatre_id") REFERENCES "public"."theatre" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-16
ALTER TABLE "public"."seats" ADD CONSTRAINT "fkjkmsbguf1drug7vlpde39vadl" FOREIGN KEY ("audi_id") REFERENCES "public"."auditorium" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset thomson:1625297229509-17
ALTER TABLE "public"."theatre" ADD CONSTRAINT "uk_dbngnxwbl03lo9qgmsft01t92" UNIQUE ("name");

-- changeset thomson:1625297229509-18
ALTER TABLE "public"."seat_booked" ADD CONSTRAINT "fkrns8kjee3gafm3m56ff0tg0tg" FOREIGN KEY ("seat_id") REFERENCES "public"."seats" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
