USE $(currentDataBase)

declare @app_schema varchar(20)
set @app_schema = 'application'

SET QUOTED_IDENTIFIER ON;

if schema_id(@app_schema) is null
BEGIN
  EXEC('create SCHEMA ' + @app_schema)
END

exec('create TABLE ' + @app_schema + '.LETTER (
  LETTER_ID int NOT NULL IDENTITY,                         -- primary key. unique key for this table.
  LETTER nvarchar(2) NOT NULL UNIQUE,                      -- sigle kannada letter. unique.
  PRIMARY KEY (LETTER_ID)
)')

exec('create TABLE ' + @app_schema + '.WORDS_LEX_INFO (
  WORD_ID int NOT NULL IDENTITY,						   -- primary key. unique word id
  LETTER_ID int NOT NULL,								   -- foreign key
  ROOT_WORD nvarchar(32) NOT NULL UNIQUE,					   -- A unique kannada word, 
  VARIATION nvarchar(32) NULL,							   -- A variation of word. It might be a another word
  EXAMPLE nvarchar(128) NULL,							   -- Example for this word
  LEX_TAG nvarchar(128) NULL,						       -- Unique lexical tag information for that word
  POS_TAG nvarchar(64) NOT NULL,						   -- Unique part of speech tag
  SYN_COUNT int DEFAULT(0),								   -- Synonymous count
  PRIMARY KEY (WORD_ID),
  FOREIGN KEY (LETTER_ID) REFERENCES ' + @app_schema + '.LETTER(LETTER_ID) ON DELETE CASCADE,
  CONSTRAINT uq_word_id_pos_tag_lex_tag UNIQUE (WORD_ID, POS_TAG, LEX_TAG)
)')

exec('create TABLE ' + @app_schema + '.WORD_GLOSS (
  GLOSS_ID int NOT NULL IDENTITY,						   -- Primary key. unique gloss id
  WORD_ID int NOT NULL,									   -- Foreign key. 
  GLOSS nvarchar(128) NOT NULL,							   -- Gloss/word description
  PRIMARY KEY (GLOSS_ID),
  FOREIGN KEY (WORD_ID) REFERENCES ' + @app_schema + '.WORDS_LEX_INFO(WORD_ID),
  CONSTRAINT uq_word_id_gloss_id UNIQUE (WORD_ID, GLOSS_ID)
)')

exec('create TABLE ' + @app_schema + '.SYNONYMOUS (
  SYN_ID int NOT NULL IDENTITY,							  -- Primary ey. unique syn id
  WORD_ID int NOT NULL,									  -- Foreign key
  SYNONYMOUS_WORD nvarchar(128) NOT NULL,				  -- Synonymous word, not unique
  PRIMARY KEY (SYN_ID),									
  FOREIGN KEY (WORD_ID) REFERENCES ' + @app_schema + '.WORDS_LEX_INFO(WORD_ID),
  CONSTRAINT uq_word_id_syn_id UNIQUE (WORD_ID, SYN_ID)
)')

exec('create INDEX WORD_ID ON ' + @app_schema + '.WORDS_LEX_INFO(
  WORD_ID
)')

exec('create INDEX LETTER_ID ON ' + @app_schema + '.LETTER(
  LETTER_ID
)')

exec('create INDEX GLOSS_ID ON ' + @app_schema + '.WORD_GLOSS(
  GLOSS_ID
)')

exec('create INDEX SYN_ID ON ' + @app_schema + '.SYNONYMOUS(
  SYN_ID
)')


-- -----------------------------------------------------