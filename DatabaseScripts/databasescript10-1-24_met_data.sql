USE [CodecademyClemens]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[ContactPerson]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Content]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Course]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Enrollment]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Module]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Progress]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Speaker]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Student]    Script Date: 10-1-2024 13:14:40 ******/
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
/****** Object:  Table [dbo].[Webcast]    Script Date: 10-1-2024 13:14:40 ******/
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
