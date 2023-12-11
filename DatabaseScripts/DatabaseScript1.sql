GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateId] [int] NOT NULL,
	[Grade] [float] NOT NULL,
	[TeacherName] [nchar](100) NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Certificate, Student, Course]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate, Student, Course](
	[CertificateId] [int] NOT NULL,
	[Email] [nchar](100) NOT NULL,
	[CourseName] [nchar](255) NOT NULL,
 CONSTRAINT [PK_Certificate, Student, Course] PRIMARY KEY CLUSTERED 
(
	[CertificateId] ASC,
	[Email] ASC,
	[CourseName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContactPerson]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContactPerson](
	[Name] [varchar](50) NOT NULL,
	[ContactPersonEmail] [nchar](100) NOT NULL,
	[Title] [nchar](255) NOT NULL,
 CONSTRAINT [PK_ContactPerson] PRIMARY KEY CLUSTERED 
(
	[Name] ASC,
	[ContactPersonEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Content]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Content](
	[ContentId] [int] NOT NULL,
	[ContentType] [nchar](14) NOT NULL,
 CONSTRAINT [PK_Content] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[Name] [nchar](255) NOT NULL,
	[Subject] [nchar](25) NOT NULL,
	[Difficulty] [nchar](20) NOT NULL,
	[IntroText] [nchar](255) NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[Student] [nchar](70) NOT NULL,
	[CourseName] [nchar](255) NOT NULL,
	[Percentage] [int] NOT NULL,
	[grade] [float] NOT NULL,
 CONSTRAINT [PK_Enrollment] PRIMARY KEY CLUSTERED 
(
	[Student] ASC,
	[CourseName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[Title] [nchar](255) NOT NULL,
	[Version] [nchar](25) NOT NULL,
	[Description] [nchar](255) NOT NULL,
	[ContactPersonEmail] [nchar](100) NOT NULL,
	[ContactPersonName] [nchar](100) NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[Title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Speaker]    Script Date: 11-12-2023 11:50:43 ******/
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
/****** Object:  Table [dbo].[Student]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [nchar](100) NOT NULL,
	[Name] [nchar](100) NOT NULL,
	[Gender] [nchar](10) NOT NULL,
	[DateOfBirth] [date] NOT NULL,
 CONSTRAINT [PK_Student] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 11-12-2023 11:50:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[URL] [varchar](255) NOT NULL,
	[Title] [nchar](255) NOT NULL,
	[Duration] [nchar](10) NOT NULL,
	[PublicationDate] [date] NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[URL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Certificate, Student, Course]  WITH CHECK ADD  CONSTRAINT [FK_Certificate, Student, Course_Certificate] FOREIGN KEY([CertificateId])
REFERENCES [dbo].[Certificate] ([CertificateId])
GO
ALTER TABLE [dbo].[Certificate, Student, Course] CHECK CONSTRAINT [FK_Certificate, Student, Course_Certificate]
GO
ALTER TABLE [dbo].[Certificate, Student, Course]  WITH CHECK ADD  CONSTRAINT [FK_Certificate, Student, Course_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
GO
ALTER TABLE [dbo].[Certificate, Student, Course] CHECK CONSTRAINT [FK_Certificate, Student, Course_Course]
GO
ALTER TABLE [dbo].[Certificate, Student, Course]  WITH CHECK ADD  CONSTRAINT [FK_Certificate, Student, Course_Student] FOREIGN KEY([Email])
REFERENCES [dbo].[Student] ([Email])
GO
ALTER TABLE [dbo].[Certificate, Student, Course] CHECK CONSTRAINT [FK_Certificate, Student, Course_Student]
GO
ALTER TABLE [dbo].[ContactPerson]  WITH CHECK ADD  CONSTRAINT [FK_ContactPerson_Module] FOREIGN KEY([Title])
REFERENCES [dbo].[Module] ([Title])
GO
ALTER TABLE [dbo].[ContactPerson] CHECK CONSTRAINT [FK_ContactPerson_Module]
GO
ALTER TABLE [dbo].[Course]  WITH CHECK ADD  CONSTRAINT [FK_Course_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
GO
ALTER TABLE [dbo].[Course] CHECK CONSTRAINT [FK_Course_Content]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Content]
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
