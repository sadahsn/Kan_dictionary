SET currentDataBase=kandic

sqlcmd -E -i 01_create_kandicdb.sql
sqlcmd -E -i 02_create_applicationSchema.sql
sqlcmd -E -i 03_insert_records.sql

pause