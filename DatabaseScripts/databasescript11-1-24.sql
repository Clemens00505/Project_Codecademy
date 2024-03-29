USE [CodecademyClemens]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateId] [int] NOT NULL,
	[CertificateAchieved] [varchar](3) NOT NULL,
	[StudentMail] [varchar](70) NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Content]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Content](
	[ContentId] [int] IDENTITY(1,1) NOT NULL,
	[ContentType] [varchar](50) NOT NULL,
	[PublicationDate] [date] NOT NULL,
	[CourseName] [varchar](255) NULL,
	[Status] [varchar](12) NOT NULL,
 CONSTRAINT [PK_Content] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[Name] [varchar](255) NOT NULL,
	[Subject] [varchar](50) NOT NULL,
	[Difficulty] [varchar](50) NOT NULL,
	[IntroText] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[StudentMail] [varchar](70) NOT NULL,
	[CourseName] [varchar](255) NOT NULL,
	[Percentage] [int] NOT NULL,
	[EnrollmentDate] [date] NOT NULL,
	[EnrollmentId] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_Enrollment] PRIMARY KEY CLUSTERED 
(
	[EnrollmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 11-1-2024 20:21:40 ******/
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
/****** Object:  Table [dbo].[Student]    Script Date: 11-1-2024 20:21:40 ******/
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
/****** Object:  Table [dbo].[ViewedContent]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ViewedContent](
	[Email] [varchar](70) NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_ViewedContent] PRIMARY KEY CLUSTERED 
(
	[Email] ASC,
	[ContentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 11-1-2024 20:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[URL] [varchar](255) NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[Duration] [varchar](50) NOT NULL,
	[SpeakerName] [varchar](50) NOT NULL,
	[SpeakerOrganisation] [varchar](50) NOT NULL,
	[ContentId] [int] NOT NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[URL] ASC,
	[SpeakerName] ASC,
	[SpeakerOrganisation] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Certificate] ([CertificateId], [CertificateAchieved], [StudentMail]) VALUES (11, N'nee', N'test2')
GO
INSERT [dbo].[Course] ([Name], [Subject], [Difficulty], [IntroText]) VALUES (N'test', N'testing', N'hard', N'welcome')
GO
SET IDENTITY_INSERT [dbo].[Enrollment] ON 

INSERT [dbo].[Enrollment] ([StudentMail], [CourseName], [Percentage], [EnrollmentDate], [EnrollmentId]) VALUES (N'test2', N'test', 0, CAST(N'2024-01-05' AS Date), 10)
INSERT [dbo].[Enrollment] ([StudentMail], [CourseName], [Percentage], [EnrollmentDate], [EnrollmentId]) VALUES (N'test2', N'test', 0, CAST(N'2024-05-03' AS Date), 11)
SET IDENTITY_INSERT [dbo].[Enrollment] OFF
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test1@gmail.com', N'test1', N'vrouw', CAST(N'4212-02-03' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test2', N'test2', N'man', CAST(N'3212-07-08' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [DateOfBirth]) VALUES (N'test5', N'test5', N'man', CAST(N'2000-03-04' AS Date))
GO
ALTER TABLE [dbo].[Certificate] ADD  CONSTRAINT [DF_Certificate_CertificateAchieved]  DEFAULT ('nee') FOR [CertificateAchieved]
GO
ALTER TABLE [dbo].[Enrollment] ADD  CONSTRAINT [DF_Enrollment_Percentage]  DEFAULT ((0)) FOR [Percentage]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment] FOREIGN KEY([CertificateId])
REFERENCES [dbo].[Enrollment] ([EnrollmentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK_Certificate_Enrollment]
GO
ALTER TABLE [dbo].[Content]  WITH CHECK ADD  CONSTRAINT [FK_Content_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Content] CHECK CONSTRAINT [FK_Content_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course1] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course1]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Student1] FOREIGN KEY([StudentMail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Student1]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Content]
GO
ALTER TABLE [dbo].[ViewedContent]  WITH CHECK ADD  CONSTRAINT [FK_ViewedContent_Content] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ViewedContent] CHECK CONSTRAINT [FK_ViewedContent_Content]
GO
ALTER TABLE [dbo].[ViewedContent]  WITH CHECK ADD  CONSTRAINT [FK_ViewedContent_Student] FOREIGN KEY([Email])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ViewedContent] CHECK CONSTRAINT [FK_ViewedContent_Student]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Content1] FOREIGN KEY([ContentId])
REFERENCES [dbo].[Content] ([ContentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Content1]
GO
