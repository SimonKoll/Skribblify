drop table paintings;
drop table words;
drop table userstats;
drop table guesses;
drop table lobby;
drop table languages;
drop table friendship;
drop table users;

create table users
(
    user_id    varchar(9) primary key,
    username   varchar(25) not null,
    status     varchar(1)  not null,
    user_img   varchar(30) DEFAULT 'default.png',
    last_login date,
    password   varchar(30)
);

create table friendship
(
    friendship_id     varchar(9) primary key,
    user_one          varchar(9) references users (user_id) on delete cascade,
    user_two          varchar(9) references users (user_id) on delete cascade,
    friendship_status varchar(1) not null default 'P'
);

create table languages
(
    language_id   varchar(9) primary key,
    language_name varchar(30) not null
);

create table lobby
(
    lobby_id       varchar(9) primary key,
    round_size     numeric(1)  not null,
    round_duration numeric(10) not null,
    language       varchar(9) references languages (language_id) on delete cascade,
    created        date        not null,
    lobby_status   varchar(1)  not null
);



create table guesses
(
    guess_id      varchar(9) primary key,
    guess_message varchar(30) not null,
    lobby_id      varchar(9) references lobby (lobby_id) on delete cascade,
    sender        varchar(9) references users (user_id) on delete cascade,
    guess_status  varchar(1)  not null
);


create table userstats
(
    user_id   varchar(9) references users (user_id) on delete cascade,
    lobby_id  varchar(9) references lobby (lobby_id) on delete cascade,
    score     numeric(10) not null,
    placement numeric(10) not null
);


create table words
(
    word_id   varchar(9) primary key,
    word_name varchar(30) not null
);

create table paintings
(
    painting_id varchar(9) primary key,
    lobby_id    varchar(9) references lobby (lobby_id) on delete cascade,
    user_id     varchar(9) references users (user_id) on delete cascade,
    img_path    varchar(30) not null
);

insert into users(user_id, username, status, user_img, last_login, password)
values ('#9BWWTWUA', 'Simon', 'A', 'default.png', '2020-05-20', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#0JJ2LZS0', 'Matthias', 'A', 'default.png', '2020-05-20', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#26DDY437', 'Tristan', 'O', 'default.png', '2020-05-19', '1234');
insert into users (user_id, username, status, user_img, last_login, password)
values ('#QXQMHMHL', 'Jan', 'O', 'default.png', '2020-05-19', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#A2383654', 'David', 'A', 'default.png', '2020-05-20', '1234');
insert into users (user_id, username, status, user_img, last_login, password)
values ('#HKD93298', 'Jonas', 'O', 'default.png', '2020-05-19', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#23231321', 'Benjamin', 'A', 'default.png', '2020-05-20', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#98765494', 'Tobias', 'A', 'default.png', '2020-05-20', '1234');
--Testuser no friends
insert into users(user_id, username, status, user_img, last_login, password)
values ('#65498765', 'SimonIbrahim', 'A', 'default.png', '2020-05-20', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#ASDWER75', 'John', 'O', 'default.png', '2020-04-30', '1234');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#POIZRSAF', 'Markus', 'o', 'default.png', '2020-04-24', '1234');



insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#A2383491', '#9BWWTWUA', '#0JJ2LZS0', 'E');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#HKD932DA', '#9BWWTWUA', '#QXQMHMHL', 'E');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#KUTZDF54', '#HKD93298', '#9BWWTWUA', 'E');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#UJTEJHDS', '#98765494', '#9BWWTWUA', 'E');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#23231491', '#9BWWTWUA', '#26DDY437', 'P');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#HKD93265', '#A2383654', '#9BWWTWUA', 'P');
insert into friendship (friendship_id, user_one, user_two, friendship_status)
values ('#98765735', '#9BWWTWUA', '#23231321', 'P');





