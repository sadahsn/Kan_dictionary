USE master;

if db_id('kandic') is not null
ALTER DATABASE kandic 
    SET SINGLE_USER 
    WITH ROLLBACK IMMEDIATE

if db_id('kandic') is not null
DROP DATABASE kandic;

CREATE DATABASE kandic;

