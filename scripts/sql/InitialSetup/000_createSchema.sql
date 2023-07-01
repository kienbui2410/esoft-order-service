-- table users
create table esoft_user(
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username varchar(50) not null,
	password varchar(100) not null,
	enabled boolean not null
);
-- table role
create table esoft_authority (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id bigint not null,
	authority varchar(50) not null,
	constraint fk_esoft_authority_user foreign key(user_id) references esoft_user(id)
);
create unique index ix_auth_username on esoft_authority (user_id,authority);

-- password: password1
insert into esoft_user(id,username,password,enabled)
values(1,'esoft_admin','$2a$10$Yk.NhA7R8yd.Rn5nvVcB1uLvcYUilj4m0bsVOEhxtsVLv/B/ih1Uq',true);
-- Role của admin
insert into esoft_authority(user_id,authority) values(1,'ROLE_ADMIN');

insert into esoft_user(id,username,password,enabled)
values(2,'esoft_user','$2a$10$Yk.NhA7R8yd.Rn5nvVcB1uLvcYUilj4m0bsVOEhxtsVLv/B/ih1Uq',true);
-- Role của user
insert into esoft_authority(user_id,authority) values(2,'ROLE_USER');

CREATE INDEX esoft_user_id ON esoft_user (id);

-- table order
CREATE TABLE esoft_order (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ref varchar(255),
    user_id bigint,
    category ENUM('LUXURY', 'SUPER_LUXURY', 'SUPREME_LUXURY'),
    service_name ENUM('PHOTO_EDITING', 'VIDEO_EDITING'),
    create_at date,
    description  varchar(255),
    note varchar(255),
    price DECIMAL(19,2) DEFAULT 0
);

CREATE INDEX esoft_order_id ON esoft_order (id);