CREATE ROLE gostack WITH LOGIN PASSWORD 'gostack';
CREATE DATABASE clk_db;
GRANT ALL PRIVILEGES ON DATABASE clk_db TO gostack;
\c clk_db
CREATE TABLE clk_user (
       id int8 not null,
        created_at timestamp,
        iterations int4 not null,
        login varchar(60) not null,
        name varchar(50),
        password varchar(60) not null,
        password_hash varchar(120) not null,
        role varchar(100) not null,
        salt varchar(120) not null,
        updated_at timestamp,
        primary key (id)
    );
GRANT ALL PRIVILEGES ON TABLE clk_user TO gostack;
INSERT INTO clk_user (id, name, login, password, password_hash, salt, iterations, role, created_at, updated_at) 
	VALUES (2, 'user', 'user', 'user', 'SB3dqX5GhaDQZgHtExekr5SQ7RgbV70=', '3KeqHpucNvA+Z+LTHAvzfQ==', 10, 'ROLE_USER', now(), now());

INSERT INTO clk_user (id, name, login, password, password_hash, salt, iterations, role, created_at, updated_at) 
	VALUES (1, 'admin', 'admin', 'admin', 'jlNdyim11FwuVgFms5zMziTWVk/xgfc=', 'K2l6TiqhufoQTTmE4nMglQ==', 10, 'ROLE_ADMIN', now(), now());
