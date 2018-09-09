USE $(currentDataBase)
GO
if user_id('kandic') is null
begin
  CREATE USER [kandic] FOR LOGIN [kandic]
end
GO
USE $(currentDataBase)
GO
ALTER USER [kandic] WITH DEFAULT_SCHEMA=[application]
GO
USE $(currentDataBase)
GO
EXEC sp_addrolemember N'db_datareader', N'kandic'
GO
USE $(currentDataBase)
GO
EXEC sp_addrolemember N'db_datawriter', N'kandic'
GO
