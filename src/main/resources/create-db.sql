create table if not exists users (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	username character varying(55) NOT NULL,
	password character varying(155) NOT NULL,
	uuid character varying(155),
	date_registered bigint default 0,
	stripe_id text,
	stripe_subscription_id text,
	disabled boolean default false
);

create table if not exists roles (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying(155) NOT NULL UNIQUE
);

create table if not exists user_permissions(
	user_id bigint REFERENCES users(id),
	permission character varying(55)
);

create table if not exists user_roles(
	role_id bigint NOT NULL REFERENCES roles(id),
	user_id bigint NOT NULL REFERENCES users(id)
);


create table if not exists videos (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	title character varying(255) NOT NULL,
	amount bigint NOT NULL default 10.0,
	key text NOT NULL,
	user_id bigint NOT NULL REFERENCES users(id)
);

