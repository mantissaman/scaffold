DROP TABLE if exists user_statuses cascade;

CREATE TABLE user_statuses (
	id serial4 NOT NULL,
	"name" varchar(100) NOT NULL,
	CONSTRAINT user_statuses_pkey PRIMARY KEY (id)
);
insert into user_statuses ("name") values
	 ('New'),
	 ('Active'),
	 ('Disabled');

DROP TABLE  if exists user_roles cascade;

CREATE TABLE user_roles (
	id serial4 NOT NULL,
	"name" varchar(100) NOT NULL,
	app_name varchar(20) NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (id)
);

insert into user_roles ("name",app_name) values
	 ('Admin','Dashboard');


DROP TABLE if exists users cascade;

CREATE TABLE users (
	id serial4 NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	email varchar(100) NULL,
	"password" varchar(100) NULL,
	status_id int4 NOT NULL,
	created_by_id int4 NULL,
	updated_by_id int4 NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	last_login_at timestamp NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_users_1 ON users USING btree (first_name);
CREATE INDEX idx_users_2 ON users USING btree (email);


ALTER TABLE users ADD CONSTRAINT user_status_id_fkey FOREIGN KEY (status_id) REFERENCES user_statuses(id);

DROP TABLE if exists user_authorizations cascade;

CREATE TABLE user_authorizations (
	user_id int4 NOT NULL,
	role_id int4 NOT NULL
);

ALTER TABLE user_authorizations ADD CONSTRAINT user_authorizations_role_id_fkey FOREIGN KEY (role_id) REFERENCES user_roles(id);
ALTER TABLE user_authorizations ADD CONSTRAINT user_authorizations_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


insert into users (first_name,last_name,email,"password",status_id,created_by_id,updated_by_id,created_at,updated_at,last_login_at) values
	 ('Shailendra','Parate','sparate@bnp.com','$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6',2,NULL,NULL,'2024-02-12 11:18:36.067429','2024-02-12 11:18:36.067429',NULL);
	 
insert into user_authorizations(user_id, role_id)
values (1, 1);
