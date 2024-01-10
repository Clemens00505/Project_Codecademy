USE [master]
GO
/****** Object:  Database [CodecademyClemens]    Script Date: 10-1-2024 11:42:21 ******/
CREATE DATABASE [CodecademyClemens]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CodecademyClemens', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\CodecademyClemens.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CodecademyClemens_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\CodecademyClemens_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [CodecademyClemens] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CodecademyClemens].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CodecademyClemens] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CodecademyClemens] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CodecademyClemens] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CodecademyClemens] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CodecademyClemens] SET ARITHABORT OFF 
GO
ALTER DATABASE [CodecademyClemens] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CodecademyClemens] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CodecademyClemens] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CodecademyClemens] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CodecademyClemens] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CodecademyClemens] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CodecademyClemens] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CodecademyClemens] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CodecademyClemens] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CodecademyClemens] SET  ENABLE_BROKER 
GO
ALTER DATABASE [CodecademyClemens] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CodecademyClemens] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CodecademyClemens] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CodecademyClemens] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CodecademyClemens] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CodecademyClemens] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CodecademyClemens] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CodecademyClemens] SET RECOVERY FULL 
GO
ALTER DATABASE [CodecademyClemens] SET  MULTI_USER 
GO
ALTER DATABASE [CodecademyClemens] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CodecademyClemens] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CodecademyClemens] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CodecademyClemens] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CodecademyClemens] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [CodecademyClemens] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'CodecademyClemens', N'ON'
GO
ALTER DATABASE [CodecademyClemens] SET QUERY_STORE = ON
GO
ALTER DATABASE [CodecademyClemens] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [CodecademyClemens]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateId] [int] IDENTITY(1,1) NOT NULL,
	[Grade] [float] NULL,
	[TeacherName] [varchar](100) NULL,
	[EnrollmentId] [int] NOT NULL,
 CONSTRAINT [PK_Certificate_1] PRIMARY KEY CLUSTERED 
(
	[CertificateId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContactPerson]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContactPerson](
	[ContactPersonName] [varchar](100) NOT NULL,
	[ContactPersonEmail] [varchar](100) NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[version] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ContactPerson] PRIMARY KEY CLUSTERED 
(
	[ContactPersonName] ASC,
	[ContactPersonEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Content]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Content](
	[ContentId] [int] IDENTITY(1,1) NOT NULL,
	[ContentType] [varchar](50) NOT NULL,
	[PublicationDate] [date] NOT NULL,
	[Status] [varchar](12) NOT NULL,
 CONSTRAINT [PK_Content] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[Name] [varchar](255) NOT NULL,
	[Subject] [varchar](50) NOT NULL,
	[Difficulty] [varchar](50) NOT NULL,
	[IntroText] [varchar](255) NOT NULL,
	[ContentId] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[StudentMail] [varchar](70) NOT NULL,
	[CourseName] [varchar](255) NOT NULL,
	[Percentage] [int] NOT NULL,
	[Grade] [float] NULL,
	[EnrollmentDate] [date] NOT NULL,
	[EnrollmentId] [int] IDENTITY(1,1) NOT NULL,
	[CertificateId] [int] NULL,
 CONSTRAINT [PK_Enrollment_1] PRIMARY KEY CLUSTERED 
(
	[StudentMail] ASC,
	[CourseName] ASC,
	[EnrollmentDate] ASC,
	[EnrollmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[Title] [varchar](255) NOT NULL,
	[Version] [varchar](50) NOT NULL,
	[Description] [varchar](255) NOT NULL,
	[ContactPersonEmail] [varchar](100) NOT NULL,
	[ContactPersonName] [varchar](100) NOT NULL,
	[ContentId] [int] NOT NULL,
	[IndexNumber] [int] NOT NULL,
	[CourseName] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[Title] ASC,
	[Version] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Progress]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Progress](
	[EnrollmentId] [int] NOT NULL,
	[Percentage] [int] NOT NULL,
 CONSTRAINT [PK_Progress_1] PRIMARY KEY CLUSTERED 
(
	[EnrollmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Speaker]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Speaker](
	[Name] [varchar](50) NOT NULL,
	[Organisation] [varchar](50) NOT NULL,
	[URL] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Speaker] PRIMARY KEY CLUSTERED 
(
	[Name] ASC,
	[Organisation] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [varchar](70) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Gender] [varchar](50) NOT NULL,
	[DateOfBirth] [date] NOT NULL,
 CONSTRAINT [PK_Student] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 10-1-2024 11:42:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[URL] [varchar](255) NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[Duration] [varchar](50) NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[URL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Certificate] ON 
GO
INSERT [dbo].[Certificate] ([CertificateId], [Grade], [TeacherName], [EnrollmentId]) VALUES (1, 2, N'test', 1)
GO
INSERT [dbo].[Certificate] ([CertificateId], [Grade], [TeacherName], [EnrollmentId]) VALUES (2, 4, N'test', 2)
GO
SET IDENTITY_INSERT [dbo].[Certificate] OFF
GO
SET IDENTITY_INSERT [dbo].[Content] ON 
GO
INSERT [dbo].[Content] ([ContentId], [ContentType], [PublicationDate], [Status]) VALUES (1, N'Webcast', CAST(N'2222-02-02' AS Date), N'online')
GO
SET IDENTITY_INSERT [dbo].[Content] OFF
GO
SET IDENTITY_INSERT [dbo].[Course] ON 
GO
INSERT [dbo].[Course] ([Name], [Subject], [Difficulty], [IntroText], [ContentId]) VALUES (N'Programmeren-1', N'programmeren', N'Medium', N'test', 1)
GO
INSERT [dbo].[Course] ([Name], [Subject], [Difficulty], [IntroText], [ContentId]) VALUES (N'Programmeren-2', N'programmeren', N'Hard', N'test', 2)
GO
SET IDENTITY_INSERT [dbo].[Course] OFF
GO
SET IDENTITY_INSERT [dbo].[Enrollment] ON 
GO
INSERT [dbo].[Enrollment] ([StudentMail], [CourseName], [Percentage], [Grade], [EnrollmentDate], [EnrollmentId], [CertificateId]) VALUES (N'cmj.stouten@student.avans.nl', N'Programmeren-1', 40, 3, CAST(N'2023-02-01' AS Date), 1, 1)
GO
SET IDENTITY_INSERT [dbo].[Enrollment] OFF
GO
INSERT [dbo].[Progress] ([EnrollmentId], [Percentage]) VALUES (1, 50)
GO
INSERT [dbo].[Progress] ([EnrollmentId], [Percentage]) VALUES (2, 33)
GO
INSERT [dbo].[Progress] ([EnrollmentId], [Percentage]) VALUES (3, 45)
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'cmj.stouten@student.avans.nl', N'Clemens', N'man', CAST(N'2005-12-29' AS Date))
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'mathijsvane@gmail.com', N'Mathijs', N'man', CAST(N'2004-12-19' AS Date))
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test@gmail.com', N'test', N'man', CAST(N'2334-02-03' AS Date))
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test2@gmail.com', N'test3', N'vrouw', CAST(N'2004-12-19' AS Date))
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test3@gmail.com', N'test3', N'man', CAST(N'1000-01-01' AS Date))
GO
ALTER TABLE [dbo].[Enrollment] ADD  CONSTRAINT [DF_Enrollment_Percentage]  DEFAULT ((0)) FOR [Percentage]
GO
ALTER TABLE [dbo].[Progress] ADD  CONSTRAINT [DF_Progress_Percentage]  DEFAULT ((0)) FOR [Percentage]
GO
ALTER TABLE [dbo].[ContactPerson]  WITH CHECK ADD  CONSTRAINT [FK_ContactPerson_Module] FOREIGN KEY([Title], [version])
REFERENCES [dbo].[Module] ([Title], [Version])
GO
ALTER TABLE [dbo].[ContactPerson] CHECK CONSTRAINT [FK_ContactPerson_Module]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Certificate] FOREIGN KEY([CertificateId])
REFERENCES [dbo].[Certificate] ([CertificateId])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Certificate]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Progress] FOREIGN KEY([EnrollmentId])
REFERENCES [dbo].[Progress] ([EnrollmentId])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Progress]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Student] FOREIGN KEY([StudentMail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Student]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Content]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[Speaker]  WITH CHECK ADD  CONSTRAINT [FK_Speaker_Webcast] FOREIGN KEY([URL])
REFERENCES [dbo].[Webcast] ([URL])
GO
ALTER TABLE [dbo].[Speaker] CHECK CONSTRAINT [FK_Speaker_Webcast]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Content]
GO
USE [master]
GO
ALTER DATABASE [CodecademyClemens] SET  READ_WRITE 
GO
