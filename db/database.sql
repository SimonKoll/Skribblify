create table users(
                      user_id varchar(9) primary key,
                      username varchar(25) not null,
                      status varchar(1) not null,
                      user_img varchar(30) DEFAULT 'default.png',
                      last_login date
);

create table friendship(
                           friendship_id numeric(10) primary key,
                           user_one varchar(9) references users(user_id) on delete cascade,
                           user_two varchar(9) references users(user_id) on delete cascade,
                           friendship_status varchar(1) not null default 'P'
);

create table languages(
                          language_id numeric(10) primary key,
                          language_name varchar(30) not null
);

create table lobby(
                      lobby_id numeric(10) primary key,
                      round_size numeric(1) not null,
                      round_duration numeric(10) not null,
                      language numeric(10) references languages(language_id) on delete cascade,
                      created date not null,
                      lobby_status varchar(1) not null
);



create table guesses(
                        guess_id numeric(10) primary key,
                        guess_message varchar(30) not null,
                        lobby_id numeric(10) references lobby(lobby_id) on delete cascade,
                        sender varchar(9) references users(user_id) on delete cascade,
                        guess_status varchar(1) not null
);


create table userstats(
                          user_id varchar(9) references users(user_id) on delete cascade,
                          lobby_id numeric(10) references lobby(lobby_id) on delete cascade,
                          score numeric(10) not null,
                          placement numeric(10) not null
);


create table words(
                      word_id numeric(10) primary key,
                      word_name varchar(30) not null
);

create table paintings(
                          painting_id numeric(10) primary key,
                          lobby_id numeric(10) references lobby(lobby_id) on delete cascade,
                          user_id varchar(9) references users(user_id) on delete cascade,
                          img_path varchar(30) not null
)