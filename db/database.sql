-- Generiert von Oracle SQL Developer Data Modeler 19.2.0.182.1216
--   am/um:        2020-03-30 16:11:57 MESZ
--   Site:      Oracle Database 11g
--   Typ:      Oracle Database 11g



CREATE TABLE friendship (
    friendshipid    NUMBER(10) NOT NULL,
    times_invited   NUMBER(3) NOT NULL
);

ALTER TABLE friendship ADD CONSTRAINT friendship_pk PRIMARY KEY ( friendshipid );

CREATE TABLE history (
    historyid       NUMBER(10) NOT NULL,
    user_username   VARCHAR2(30) NOT NULL,
    lobby_lobbyid   NUMBER NOT NULL,
    points          NUMBER(4) NOT NULL,
    paintid         NUMBER NOT NULL
);

ALTER TABLE history ADD CONSTRAINT history_pk PRIMARY KEY ( historyid );

CREATE TABLE language (
    languageid      NUMBER(1) NOT NULL,
    language_name   VARCHAR2(40)
);

ALTER TABLE language ADD CONSTRAINT language_pk PRIMARY KEY ( languageid );

CREATE TABLE lobby (
    lobbyid    NUMBER NOT NULL,
    "date"     DATE NOT NULL,
    rounds     NUMBER(1) NOT NULL,
    "size"     NUMBER(1) NOT NULL,
    duration   DATE NOT NULL
);

ALTER TABLE lobby ADD CONSTRAINT lobby_pk PRIMARY KEY ( lobbyid );

CREATE TABLE message_history (
    messageid           NUMBER(10) NOT NULL,
    message             VARCHAR2(100) NOT NULL,
    history_historyid   NUMBER(10) NOT NULL
);

ALTER TABLE message_history ADD CONSTRAINT message_history_pk PRIMARY KEY ( messageid );

CREATE TABLE paintings (
    paintid             NUMBER(10) NOT NULL,
    path                VARCHAR2(100) NOT NULL,
    history_historyid   NUMBER(10)
);

ALTER TABLE paintings ADD CONSTRAINT paintings_pk PRIMARY KEY ( paintid );

CREATE TABLE relation_7 (
    lobby_lobbyid   NUMBER NOT NULL,
    words_wordid    NUMBER NOT NULL
);

ALTER TABLE relation_7 ADD CONSTRAINT relation_7_pk PRIMARY KEY ( lobby_lobbyid,
                                                                  words_wordid );

CREATE TABLE relation_8 (
    friendship_friendshipid   NUMBER(10) NOT NULL,
    user_username             VARCHAR2(30) NOT NULL
);

ALTER TABLE relation_8 ADD CONSTRAINT relation_8_pk PRIMARY KEY ( friendship_friendshipid,
                                                                  user_username );

CREATE TABLE "user" (
    username       VARCHAR2(30) NOT NULL,
    userid         NUMBER NOT NULL,
    password       VARCHAR2(25) NOT NULL,
    last_login     DATE,
    user_deleted   CHAR(1) NOT NULL
);

ALTER TABLE "user" ADD CONSTRAINT user_pk PRIMARY KEY ( username );

CREATE TABLE words (
    wordid                NUMBER NOT NULL,
    word                  VARCHAR2(25) NOT NULL,
    times_used            NUMBER(10) NOT NULL,
    language_languageid   NUMBER(1) NOT NULL
);

ALTER TABLE words ADD CONSTRAINT words_pk PRIMARY KEY ( wordid );

ALTER TABLE history
    ADD CONSTRAINT history_lobby_fk FOREIGN KEY ( lobby_lobbyid )
        REFERENCES lobby ( lobbyid );

ALTER TABLE history
    ADD CONSTRAINT history_user_fk FOREIGN KEY ( user_username )
        REFERENCES "user" ( username );

ALTER TABLE message_history
    ADD CONSTRAINT message_history_history_fk FOREIGN KEY ( history_historyid )
        REFERENCES history ( historyid );

ALTER TABLE paintings
    ADD CONSTRAINT paintings_history_fk FOREIGN KEY ( history_historyid )
        REFERENCES history ( historyid );

ALTER TABLE relation_7
    ADD CONSTRAINT relation_7_lobby_fk FOREIGN KEY ( lobby_lobbyid )
        REFERENCES lobby ( lobbyid );

ALTER TABLE relation_7
    ADD CONSTRAINT relation_7_words_fk FOREIGN KEY ( words_wordid )
        REFERENCES words ( wordid );

ALTER TABLE relation_8
    ADD CONSTRAINT relation_8_friendship_fk FOREIGN KEY ( friendship_friendshipid )
        REFERENCES friendship ( friendshipid );

ALTER TABLE relation_8
    ADD CONSTRAINT relation_8_user_fk FOREIGN KEY ( user_username )
        REFERENCES "user" ( username );

ALTER TABLE words
    ADD CONSTRAINT words_language_fk FOREIGN KEY ( language_languageid )
        REFERENCES language ( languageid );



-- Zusammenfassungsbericht für Oracle SQL Developer Data Modeler:
--
-- CREATE TABLE                            10
-- CREATE INDEX                             0
-- ALTER TABLE                             19
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
--
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
--
-- REDACTION POLICY                         0
--
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
--
-- ERRORS                                   0
-- WARNINGS                                 0
