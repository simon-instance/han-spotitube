-- Creating tables

CREATE TABLE `user` (
    `user` VARCHAR(200) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    token VARCHAR(14) NOT NULL,
--
    CONSTRAINT pk_user PRIMARY KEY(token),
    CONSTRAINT un_user UNIQUE(`user`)
);

CREATE TABLE `playlist` (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    `name` varchar(200) NOT NULL,
    owner varchar(200) NOT NULL,

    CONSTRAINT pk_playlist PRIMARY KEY(id),
    CONSTRAINT fk_owner FOREIGN KEY(owner) REFERENCES `user`(`user`)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE `track` (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title varchar(200) NOT NULL,
    performer varchar(200) NOT NULL,
    duration int NOT NULL,
    album varchar(400),
    playcount int,
    publicationDate date,
    description varchar(500),
    offlineAvailable bit NOT NULL,

    CONSTRAINT pk_track PRIMARY KEY(id)
);

CREATE TABLE `playlistTracks` (
    playlistId int NOT NULL,
    trackId int NOT NULL,

     CONSTRAINT fk_playlist_ref FOREIGN KEY(playlistId) REFERENCES playlist(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_track_ref FOREIGN KEY(trackId) REFERENCES track(id)
);

-- Inserting data

-- inserting values for user table
INSERT INTO `user` (`user`, password, token)
VALUES ('john_doe', '123456', '1234-1234-1234'),
    ('jane_doe', 'abcdef', '1111-1111-1111'),
    ('jim_smith', 'qwerty', '2222-2222-2222'),
    ('judy_davis', 'mypassword', '3333-3333-3333');

-- inserting values for playlist table
INSERT INTO `playlist` (`name`, owner)
VALUES ('My Playlist', 'john_doe'),
    ('Rock Playlist', 'jane_doe'),
    ('Workout Playlist', 'jim_smith'),
    ('Relaxing Playlist', 'judy_davis');

-- inserting values for track table
INSERT INTO `track` (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable)
VALUES ('Stairway to Heaven', 'Led Zeppelin', 482, 'Led Zeppelin IV', 100000, '1971-11-08', 'One of the most famous rock songs of all time.', 1),
    ('Bohemian Rhapsody', 'Queen', 354, 'A Night at the Opera', 90000, '1980-06-06', NULL, 1),
    ('Shape of You', 'Ed Sheeran', 233, ':', 200000,  '1976-05-05', NULL, 1),
    ('Hello', 'Adele', 295, '25', 150000, '1973-05-08', NULL, 0);

-- inserting values for playlistTracks table
INSERT INTO `playlistTracks` (playlistId, trackId)
VALUES (1, 1),
    (1, 2),
    (2, 1),
    (2, 2),
    (2, 4),
    (3, 3),
    (3, 4);
