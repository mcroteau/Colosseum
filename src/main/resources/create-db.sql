create table if not exists alpha_products(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    nickname character varying (255),
	stripe_id text
);

create table if not exists alpha_plans(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	stripe_id text,
    amount bigint default 0,
    nickname character varying (255),
    description text,
    project_limit bigint,
	alpha_product_id bigint NOT NULL REFERENCES alpha_products(id)
);

create table if not exists users (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	username character varying(55) NOT NULL,
	password character varying(155) NOT NULL,
	uuid character varying(155),
	date_registered bigint default 0,
	stripe_id text,
	stripe_subscription_id text,
	disabled boolean default false,
	okay_plan_id bigint REFERENCES okay_plans(id)
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
	name character varying(255) NOT NULL,
	uri text NOT NULL,
	user_id bigint NOT NULL REFERENCES users(id)
);

create table if not exists project_status (
	project_id bigint NOT NULL REFERENCES videos(id),
    status_code bigint NOT NULL,
    response_sum decimal default 0.0,
    latest_response decimal default 0.0,
    timeouts bigint default 0,
    notified boolean default false,
    notified_count bigint default 0,
    total_http_validations bigint default 0,
    operational_http_validations bigint default 0,
    validation_date bigint default 0,
    initial_saving boolean default true
);

create table if not exists project_emails (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	email character varying(255) NOT NULL,
	project_id bigint NOT NULL REFERENCES videos(id)
);

create table if not exists project_phones (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	phone character varying(255) NOT NULL,
	project_id bigint NOT NULL REFERENCES videos(id)
);

