USE [DeanOffice]
GO
CREATE USER [DeanOffice_admin] FOR LOGIN [DeanOffice_admin]
GO
USE [DeanOffice]
GO
ALTER ROLE [db_datareader] ADD MEMBER [DeanOffice_admin]
GO
USE [DeanOffice]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [DeanOffice_admin]
GO
USE [DeanOffice]
GO
ALTER ROLE [db_ddladmin] ADD MEMBER [DeanOffice_admin]
GO
USE [DeanOffice]
GO
ALTER ROLE [db_owner] ADD MEMBER [DeanOffice_admin]
GO
