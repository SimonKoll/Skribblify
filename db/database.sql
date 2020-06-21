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
    lobby_status   varchar(1) not null,
    created        varchar(30)        not null,
    maxplayers     numeric(10)
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
values ('#9BWWTWUA', 'Simon', 'A', 'default.png', '2020-05-20', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#0JJ2LZS0', 'Matthias', 'A', 'default.png', '2020-05-20', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#26DDY437', 'Tristan', 'O', 'default.png', '2020-05-19', '123456');
insert into users (user_id, username, status, user_img, last_login, password)
values ('#QXQMHMHL', 'Jan', 'O', 'default.png', '2020-05-19', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#A2383654', 'David', 'A', 'default.png', '2020-05-20', '123456');
insert into users (user_id, username, status, user_img, last_login, password)
values ('#HKD93298', 'Jonas', 'O', 'default.png', '2020-05-19', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#23231321', 'Benjamin', 'A', 'default.png', '2020-05-20', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#98765494', 'Tobias', 'A', 'default.png', '2020-05-20', '123456');
--Testuser no friends
insert into users(user_id, username, status, user_img, last_login, password)
values ('#65498765', 'SimonIbrahim', 'A', 'default.png', '2020-05-20', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#ASDWER75', 'John', 'O', 'default.png', '2020-04-30', '123456');
insert into users(user_id, username, status, user_img, last_login, password)
values ('#POIZRSAF', 'Markus', 'o', 'default.png', '2020-04-24', '123456');



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

--insert languages
insert into languages values ('#9BWWTWUL', 'German');

--insert words
insert into words values('#9BWTTWUL', 'Wasserflasche');
insert into words values('#9BWTWWUL', 'Sonnenblume');
insert into words values('#9BWLTWUL', 'Ampel');
insert into words values('#9BWHTWUL', 'Sonnenuntergang');
insert into words values('#9BW2TWUL', 'Rucksack');
insert into words values('#9BWT2WUL', 'Berg');
insert into words values('#9BWLT2UL', 'Regen');
insert into words values('#9BWHTW2L', 'Baum');
insert into words values('#9BWTTWU2', 'Liebe');
insert into words values('#2BWTWWUL', 'Ordner');
insert into words values('#92WLTWUL', 'Papier');
insert into words values('#9B2HTWUL', 'Bücherregal');
insert into words values('#9BW3TWUL', 'Schiff');
insert into words values('#9BWT3WUL', 'Fluss');
insert into words values('#9BWLT3UL', 'Drohne');
insert into words values('#9BWHTW3L', 'Schloss');
insert into words values('#9BWHAWU3', 'Computer');
insert into words values('#9BWVASUL', 'Musik');
insert into words values('#9BEHTWUL', 'Taschenrechner');
insert into words values('#9BWVTDUL', 'Spitzer');
insert into words values('#9BWATWUL', 'Radiergummi');
insert into words values('#9BWDTWUL', 'Bleistift');
insert into words values('#9BWHDWUL', 'Führerschein');
insert into words values('#9BWHTWDL', 'Geldschein');
insert into words values('#9BWA5WUL', 'Karte');
insert into words values('#9BWHTWUD', 'Fernseher');
insert into words values('#1BWHTWUD', 'Monitor');
insert into words values('#2BWHTWUD', 'Klebeband');
insert into words values('#3BWHTWUD', 'Tachentuch');
insert into words values('#4BWHTWUD', 'Maus');
insert into words values('#5BWHTWUD', 'Tastatur');
insert into words values('#6BWHTWUD', 'Lampe');
insert into words values('#8BWHTWUD', 'Schreibtisch');
insert into words values('#11WHTWUD', 'Kopfkissen');
insert into words values('#12WHTWUD', 'Tablette');
insert into words values('#13WHTWUD', 'Chips');
insert into words values('#14WHTWUD', 'Ladekabel');
insert into words values('#15WHTWUD', 'Fenster');
insert into words values('#16WHTWUD', 'Protokoll');
insert into words values('#17WHTWUD', 'Schule');
insert into words values('#18WHTWUD', 'Nagelfeile');
insert into words values('#19WHTWUD', 'Festplatte');
insert into words values('#20WHTWUD', 'Schraubenzieher');
insert into words values('#21WHTWUD', 'Hammer');
insert into words values('#22WHTWUD', 'Amboss');
insert into words values('#23WHTWUD', 'Werkstatt');
insert into words values('#24WHTWUD', 'Auto');
insert into words values('#25WHTWUD', 'Fahrrad');
insert into words values('#26WHTWUD', 'Tasse');
insert into words values('#27WHTWUD', 'Buch');
insert into words values('#28WHTWUD', 'Supermarkt');
insert into words values('#29WHTWUD', 'Radio');
insert into words values('#30WHTWUD', 'Lautsprecher');
insert into words values('#31WHTWUD', 'Statistik');
insert into words values('#32WHTWUD', 'Wörterbuch');
insert into words values('#33WHTWUD', 'Comic');
insert into words values('#34WHTWUD', 'Antrag');
insert into words values('#35WHTWUD', 'Kopfhörer');
insert into words values('#36WHTWUD', 'Bewerbungsgespräch');
insert into words values('#37WHTWUD', 'Fussel');
insert into words values('#38WHTWUD', 'Armband');
insert into words values('#39WHTWUD', 'Anzug');
insert into words values('#40WHTWUD', 'Angestellter');
insert into words values('#41WHTWUD', 'Hose');
insert into words values('#42WHTWUD', 'Schuhe');
insert into words values('#43WHTWUD', 'Halskette');
insert into words values('#44WHTWUD', 'Kettensäge');
insert into words values('#45WHTWUD', 'Brunnen');
insert into words values('#46WHTWUD', 'Pumpe');
insert into words values('#47WHTWUD', 'Waage');
insert into words values('#48WHTWUD', 'Vase');
insert into words values('#49WHTWUD', 'Roman');
insert into words values('#50WHTWUD', 'Fotobuch');
insert into words values('#51WHTWUD', 'Wohnwagen');
insert into words values('#52WHTWUD', 'Wohnmobil');
insert into words values('#53WHTWUD', 'Motorrad');
insert into words values('#54WHTWUD', 'Magnet');
insert into words values('#55WHTWUD', 'Handtuch');
insert into words values('#56WHTWUD', 'Wiese');
insert into words values('#57WHTWUD', 'Wüste');
insert into words values('#58WHTWUD', 'Stock');
insert into words values('#59WHTWUD', 'Bahnhof');
insert into words values('#60WHTWUD', 'Bett');
insert into words values('#61WHTWUD', 'Bogen');
insert into words values('#62WHTWUD', 'Brief');
insert into words values('#63WHTWUD', 'Buchstaben');
insert into words values('#64WHTWUD', 'Bus');
insert into words values('#65WHTWUD', 'Flugzeug');
insert into words values('#66WHTWUD', 'Stift');
insert into words values('#67WHTWUD', 'Karten');
insert into words values('#68WHTWUD', 'Gesicht');
insert into words values('#69WHTWUD', 'Haar');
insert into words values('#70WHTWUD', 'Hund');
insert into words values('#71WHTWUD', 'Katze');
insert into words values('#72WHTWUD', 'Maus');
insert into words values('#73WHTWUD', 'Elefant');
insert into words values('#74WHTWUD', 'Kanne');
insert into words values('#75WHTWUD', 'Löffel');
insert into words values('#76WHTWUD', 'Mund');
insert into words values('#77WHTWUD', 'Obst');
insert into words values('#78WHTWUD', 'Schnee');
insert into words values('#79WHTWUD', 'Sonnenstrahl');
insert into words values('#80WHTWUD', 'Container');
insert into words values('#81WHTWUD', 'Schokolade');
insert into words values('#82WHTWUD', 'Sofa');
insert into words values('#83WHTWUD', 'Tee');
insert into words values('#84WHTWUD', 'Tomate');
insert into words values('#85WHTWUD', 'Treppe');
insert into words values('#86WHTWUD', 'Uhr');
insert into words values('#87WHTWUD', 'Video');
insert into words values('#88WHTWUD', 'Vogel');
insert into words values('#89WHTWUD', 'Wurst');
insert into words values('#90WHTWUD', 'Käse');
insert into words values('#91WHTWUD', 'Schinken');
insert into words values('#92WHTWUD', 'Speck');
insert into words values('#93WHTWUD', 'Kuh');
insert into words values('#94WHTWUD', 'Huhn');
insert into words values('#95WHTWUD', 'Schwein');
insert into words values('#96WHTWUD', 'Reh');
insert into words values('#97WHTWUD', 'Hase');
insert into words values('#98WHTWUD', 'Ei');
insert into words values('#99WHTWUD', 'Schnitzel');

insert into LANGUAGES (language_id, language_name) values ('#DEDEDEDE', 'DE');








