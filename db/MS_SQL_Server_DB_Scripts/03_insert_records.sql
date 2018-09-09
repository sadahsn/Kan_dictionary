USE $(currentDataBase)
-- Statements to insert records in to LETTER table ---
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಅ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಆ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಇ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಈ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಉ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಊ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಋ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಎ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಏ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಐ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಒ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಓ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಔ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಅಂ')
INSERT INTO [application].[LETTER] ([LETTER]) VALUES (N'ಅಃ')


-- Statements to insert records in to WORDS_LEX_INFO table ---
INSERT INTO [application].[WORDS_LEX_INFO]   ([LETTER_ID],[ROOT_WORD],[VARIATION],[EXAMPLE],[LEX_TAG],[POS_TAG],[SYN_COUNT])  VALUES (1, N'ಅಂಕ', NULL, NULL, 'N-PROP', N'ನಾಮಪದ',4)

-- Statements to insert records in to WORD_GLOSS table ---
INSERT INTO [application].[WORD_GLOSS] ([WORD_ID],[GLOSS])  VALUES  (1, N'ನಾಟಕದಲ್ಲಿ  ಒಂದು ವಿಭಾಗ.')
INSERT INTO [application].[WORD_GLOSS] ([WORD_ID],[GLOSS])  VALUES  (1, N'ಪರೀಕ್ಷೆಯಲ್ಲಿ ನೀಡುವ ಗುಣಾಂಕ.')

-- Statements to insert records in to SYNONYMOUS table ---
INSERT INTO [application].[SYNONYMOUS] ([WORD_ID],[SYNONYMOUS_WORD]) VALUES (1,N'ಗುರುತು.')
INSERT INTO [application].[SYNONYMOUS] ([WORD_ID],[SYNONYMOUS_WORD]) VALUES (1,N'ತೊಡೆ.')
INSERT INTO [application].[SYNONYMOUS] ([WORD_ID],[SYNONYMOUS_WORD]) VALUES (1,N'ಹೆಸರು.')
INSERT INTO [application].[SYNONYMOUS] ([WORD_ID],[SYNONYMOUS_WORD]) VALUES (1,N'ಯುದ್ಧ.')


GO
