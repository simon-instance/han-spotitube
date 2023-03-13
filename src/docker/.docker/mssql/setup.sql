USE master
GO

DROP DATABASE IF EXISTS Spotitube
GO

CREATE DATABASE Spotitube
GO

USE Spotitube
GO

DROP USER IF EXISTS [applicatie]
GO

CREATE LOGIN [applicatie] WITH PASSWORD = 'Appl1catie';
GO

-- Create the new user and assign it to the role
CREATE USER [applicatie] FOR LOGIN [applicatie];
GO

CREATE ROLE dbo_role;
GO
GRANT SELECT, UPDATE, DELETE, INSERT ON SCHEMA::dbo TO dbo_role;
GO

-- Assign the role to the 'applicatie' user
EXEC sp_addrolemember 'dbo_role', 'applicatie';
GO

CREATE TABLE [user] (
    [user] varchar(200) NOT NULL,
    [password] varchar(1000) NOT NULL,

    CONSTRAINT [pk_user] PRIMARY KEY([user])
    )
    GO
CREATE TABLE [playlist](
    [name] varchar(200) NOT NULL,
    [owner] varchar(200) NOT NULL,

    CONSTRAINT [pk_playlist] PRIMARY KEY([name], [owner]),
    CONSTRAINT [fk_owner] FOREIGN KEY([owner]) REFERENCES [user]([user])
    ON UPDATE CASCADE
    ON DELETE NO ACTION
    )
    GO
CREATE TABLE [track](
    [id] int NOT NULL IDENTITY(1,1),
    [title] varchar(200) NOT NULL,
    [performer] varchar(200) NOT NULL,
    [duration] int NOT NULL,
    [album] varchar(400),
    [playcount] int,
    [publicationDate] date,
    [description] varchar(500),
    [offlineAvailable] bit NOT NULL,

    CONSTRAINT [pk_track] PRIMARY KEY([id])
    )
    GO
CREATE TABLE [playlistTracks](
    [name] varchar(200) NOT NULL,
    [owner] varchar(200) NOT NULL,
    [trackId] int NOT NULL,

    CONSTRAINT [fk_playlist_ref] FOREIGN KEY([name], [owner]) REFERENCES [playlist]([name], [owner]),
    CONSTRAINT [fk_track_ref] FOREIGN KEY(trackId) REFERENCES [track]([id])
    )
    GO

