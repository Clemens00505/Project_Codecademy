CREATE DATABASE CodecademyClemens
GO

USE CodecademyClemens
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateId] [int] IDENTITY(1,1) NOT NULL,
	[EnrollmentId] [int] NULL,
	[StudentMail] [varchar](70) NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[CourseId] [int] IDENTITY(1,1) NOT NULL,
	[CourseName] [varchar](255) NOT NULL,
	[Subject] [varchar](100) NOT NULL,
	[IntroText] [varchar](max) NOT NULL,
	[Difficulty] [varchar](9) NOT NULL,
 CONSTRAINT [PK_Course_1] PRIMARY KEY CLUSTERED 
(
	[CourseId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[StudentMail] [varchar](70) NOT NULL,
	[CourseId] [int] NOT NULL,
	[Percentage] [int] NOT NULL,
	[EnrollmentDate] [date] NOT NULL,
	[EnrollmentId] [int] IDENTITY(1,1) NOT NULL,
	[HasCertificate] [int] NOT NULL,
 CONSTRAINT [PK_Enrollment] PRIMARY KEY CLUSTERED 
(
	[EnrollmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EnrollmentModules]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EnrollmentModules](
	[EnrollmentId] [int] NOT NULL,
	[CourseId] [int] NOT NULL,
	[ContentId] [int] NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[Version] [int] NOT NULL,
	[Progress] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[ContentId] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[Version] [int] NOT NULL,
	[ContactPersonName] [varchar](255) NOT NULL,
	[Description] [varchar](max) NOT NULL,
	[ContactPersonMail] [varchar](255) NOT NULL,
	[PublicationDate] [date] NOT NULL,
	[Status] [varchar](12) NOT NULL,
	[IndexNumber] [int] NOT NULL,
	[CourseId] [int] NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC,
	[Title] ASC,
	[Version] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [varchar](70) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Gender] [varchar](50) NOT NULL,
	[HouseNumber] [int] NULL,
	[City] [varchar](100) NULL,
	[PostalCode] [varchar](7) NULL,
	[Country] [varchar](100) NULL,
	[DateOfBirth] [date] NOT NULL,
 CONSTRAINT [PK_Student] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[StudentWebcastProgress]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StudentWebcastProgress](
	[StudentWebcastProgressId] [int] IDENTITY(1,1) NOT NULL,
	[StudentMail] [varchar](70) NOT NULL,
	[WebcastContentId] [int] NOT NULL,
	[ProgressPercentage] [int] NOT NULL,
 CONSTRAINT [PK_StudentWebcastProgress] PRIMARY KEY CLUSTERED 
(
	[StudentWebcastProgressId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ViewedContent]    Script Date: 29-3-2024 21:50:59 ******/
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
/****** Object:  Table [dbo].[ViewedWebcasts]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ViewedWebcasts](
	[ContentId] [int] NOT NULL,
	[StudentMail] [varchar](70) NOT NULL,
 CONSTRAINT [PK_ViewedWebcasts] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC,
	[StudentMail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 29-3-2024 21:50:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[ContentId] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](255) NOT NULL,
	[Description] [varchar](max) NOT NULL,
	[SpeakerName] [varchar](100) NOT NULL,
	[SpeakerOrganisation] [varchar](100) NOT NULL,
	[PublicationDate] [date] NOT NULL,
	[Status] [varchar](12) NOT NULL,
	[URL] [varchar](255) NOT NULL,
	[TimesViewed] [int] NOT NULL,
 CONSTRAINT [PK_Webcast_1] PRIMARY KEY CLUSTERED 
(
	[ContentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Certificate] ON 

INSERT [dbo].[Certificate] ([CertificateId], [EnrollmentId], [StudentMail]) VALUES (13, 10, N'Jasper@gmail.com')
SET IDENTITY_INSERT [dbo].[Certificate] OFF
GO
SET IDENTITY_INSERT [dbo].[Course] ON 

INSERT [dbo].[Course] ([CourseId], [CourseName], [Subject], [IntroText], [Difficulty]) VALUES (1, N'INTRO java programmeren', N'java', N'Deze cursus is een introductie in het programmeren in java. De cursus gaat over de belangrijkste basics binnen de programmeertaal', N'BEGINNER')
INSERT [dbo].[Course] ([CourseId], [CourseName], [Subject], [IntroText], [Difficulty]) VALUES (2, N'Java 2', N'java', N'Deze cursus is een vervolg op "INTRO java programmeren" ', N'GEVORDERD')
INSERT [dbo].[Course] ([CourseId], [CourseName], [Subject], [IntroText], [Difficulty]) VALUES (4, N'Java 3', N'java', N'Deze cursus is een vervolg op "Java 2" ', N'GEVORDERD')
INSERT [dbo].[Course] ([CourseId], [CourseName], [Subject], [IntroText], [Difficulty]) VALUES (5, N'The basics of CyberSecurity', N'CyberSecurity', N'Deze cursus gaat over de basis van Cybersecurity.', N'BEGINNER')
SET IDENTITY_INSERT [dbo].[Course] OFF
GO
SET IDENTITY_INSERT [dbo].[Enrollment] ON 

INSERT [dbo].[Enrollment] ([StudentMail], [CourseId], [Percentage], [EnrollmentDate], [EnrollmentId], [HasCertificate]) VALUES (N'Henk@gmail.com', 2, 0, CAST(N'2024-03-10' AS Date), 8, 0)
INSERT [dbo].[Enrollment] ([StudentMail], [CourseId], [Percentage], [EnrollmentDate], [EnrollmentId], [HasCertificate]) VALUES (N'Jasper@gmail.com', 1, 100, CAST(N'2024-03-01' AS Date), 10, 1)
SET IDENTITY_INSERT [dbo].[Enrollment] OFF
GO
INSERT [dbo].[EnrollmentModules] ([EnrollmentId], [CourseId], [ContentId], [Title], [Version], [Progress]) VALUES (10, 1, 1, N'INTRO programmeren in java - Module 1', 1, 0)
INSERT [dbo].[EnrollmentModules] ([EnrollmentId], [CourseId], [ContentId], [Title], [Version], [Progress]) VALUES (10, 1, 3, N'INTRO programmeren in java - Module 2', 1, 0)
INSERT [dbo].[EnrollmentModules] ([EnrollmentId], [CourseId], [ContentId], [Title], [Version], [Progress]) VALUES (10, 1, 4, N'INTRO programmeren in java - Module 3', 2, 0)
INSERT [dbo].[EnrollmentModules] ([EnrollmentId], [CourseId], [ContentId], [Title], [Version], [Progress]) VALUES (10, 1, 5, N'INTRO programmeren java - module 4', 1, 0)
GO
SET IDENTITY_INSERT [dbo].[Module] ON 

INSERT [dbo].[Module] ([ContentId], [Title], [Version], [ContactPersonName], [Description], [ContactPersonMail], [PublicationDate], [Status], [IndexNumber], [CourseId]) VALUES (1, N'INTRO programmeren in java - Module 1', 1, N'henk', N'Eerste module in INTRO programmeren in java', N'henk@gmail.com', CAST(N'2024-01-30' AS Date), N'ACTIEF', 1, 1)
INSERT [dbo].[Module] ([ContentId], [Title], [Version], [ContactPersonName], [Description], [ContactPersonMail], [PublicationDate], [Status], [IndexNumber], [CourseId]) VALUES (3, N'INTRO programmeren in java - Module 2', 1, N'henk', N'programmeren in java', N'henk@gmail.com', CAST(N'2024-02-27' AS Date), N'ACTIEF', 2, 1)
INSERT [dbo].[Module] ([ContentId], [Title], [Version], [ContactPersonName], [Description], [ContactPersonMail], [PublicationDate], [Status], [IndexNumber], [CourseId]) VALUES (4, N'INTRO programmeren in java - Module 3', 2, N'henk', N'java programmeren basic', N'henk@gmail.com', CAST(N'2024-02-27' AS Date), N'ACTIEF', 3, 1)
INSERT [dbo].[Module] ([ContentId], [Title], [Version], [ContactPersonName], [Description], [ContactPersonMail], [PublicationDate], [Status], [IndexNumber], [CourseId]) VALUES (5, N'INTRO programmeren java - module 4', 1, N'bart', N'java ', N'bart@gmail.com', CAST(N'2024-02-23' AS Date), N'ACTIEF', 4, 1)
INSERT [dbo].[Module] ([ContentId], [Title], [Version], [ContactPersonName], [Description], [ContactPersonMail], [PublicationDate], [Status], [IndexNumber], [CourseId]) VALUES (6, N'The The basics of CyberSecurity - module 1', 1, N'Bert', N'Eerste module in CyberSecurity', N'bert@gmail.com', CAST(N'2024-03-28' AS Date), N'CONCEPT', 1, 5)
SET IDENTITY_INSERT [dbo].[Module] OFF
GO
INSERT [dbo].[Student] ([Email], [Name], [Gender], [HouseNumber], [City], [PostalCode], [Country], [DateOfBirth]) VALUES (N'Eva@gmail.com', N'Eva', N'VROUW', 12, N'Groningen', N'2854DE', N'Nederland', CAST(N'2002-03-20' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [HouseNumber], [City], [PostalCode], [Country], [DateOfBirth]) VALUES (N'Henk@gmail.com', N'Henk', N'MAN', 5, N'Breda', N'3456AE', N'Nederland', CAST(N'2006-03-28' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [HouseNumber], [City], [PostalCode], [Country], [DateOfBirth]) VALUES (N'Jasper@gmail.com', N'Jasper', N'MAN', 1, N'Tilburg', N'9876KL', N'Nederland', CAST(N'2006-03-31' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [HouseNumber], [City], [PostalCode], [Country], [DateOfBirth]) VALUES (N'mathijs@gmail.com', N'Mathijs', N'MAN', 16, N'Gilze', N'5126 XB', N'Nederland', CAST(N'2004-12-19' AS Date))
INSERT [dbo].[Student] ([Email], [Name], [Gender], [HouseNumber], [City], [PostalCode], [Country], [DateOfBirth]) VALUES (N'Max@gmail.com', N'Max', N'MAN', 8, N'Zoetermeer', N'4892KF', N'Nederland', CAST(N'1997-12-24' AS Date))
GO
SET IDENTITY_INSERT [dbo].[StudentWebcastProgress] ON 

INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (1, N'Henk@gmail.com', 6, 0)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (2, N'Henk@gmail.com', 7, 0)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (3, N'Jasper@gmail.com', 6, 71)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (4, N'Jasper@gmail.com', 7, 0)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (5, N'Jasper@gmail.com', 7, 0)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (8, N'mathijs@gmail.com', 6, 49)
INSERT [dbo].[StudentWebcastProgress] ([StudentWebcastProgressId], [StudentMail], [WebcastContentId], [ProgressPercentage]) VALUES (9, N'mathijs@gmail.com', 7, 42)
SET IDENTITY_INSERT [dbo].[StudentWebcastProgress] OFF
GO
SET IDENTITY_INSERT [dbo].[Webcast] ON 

INSERT [dbo].[Webcast] ([ContentId], [Title], [Description], [SpeakerName], [SpeakerOrganisation], [PublicationDate], [Status], [URL], [TimesViewed]) VALUES (6, N'Firewall', N'CyberSecurity', N'Henk', N'Avans', CAST(N'2024-03-28' AS Date), N'CONCEPT', N'Url:', 7)
INSERT [dbo].[Webcast] ([ContentId], [Title], [Description], [SpeakerName], [SpeakerOrganisation], [PublicationDate], [Status], [URL], [TimesViewed]) VALUES (7, N'Variables', N'Java variables', N'Bas', N'Avans', CAST(N'2024-03-26' AS Date), N'ACTIEF', N'Url:', 1)
INSERT [dbo].[Webcast] ([ContentId], [Title], [Description], [SpeakerName], [SpeakerOrganisation], [PublicationDate], [Status], [URL], [TimesViewed]) VALUES (8, N'UML', N'UML Diagrams', N'Peter', N'Avans', CAST(N'2024-03-29' AS Date), N'GEARCHIVEERD', N'Url:', 23)
INSERT [dbo].[Webcast] ([ContentId], [Title], [Description], [SpeakerName], [SpeakerOrganisation], [PublicationDate], [Status], [URL], [TimesViewed]) VALUES (9, N'ERD / RDO Basics', N'ERD / RDO Database', N'Bert', N'CM.COM', CAST(N'2023-09-20' AS Date), N'CONCEPT', N'Url:', 0)
SET IDENTITY_INSERT [dbo].[Webcast] OFF
GO
ALTER TABLE [dbo].[Enrollment] ADD  CONSTRAINT [DF_Enrollment_Percentage]  DEFAULT ((0)) FOR [Percentage]
GO
ALTER TABLE [dbo].[Enrollment] ADD  CONSTRAINT [DF_Enrollment_CertificateCreated]  DEFAULT ((0)) FOR [HasCertificate]
GO
ALTER TABLE [dbo].[EnrollmentModules] ADD  CONSTRAINT [DF_EnrollmentModules_Progress]  DEFAULT ((0)) FOR [Progress]
GO
ALTER TABLE [dbo].[Module] ADD  CONSTRAINT [DF_Module_CourseId]  DEFAULT (NULL) FOR [CourseId]
GO
ALTER TABLE [dbo].[StudentWebcastProgress] ADD  CONSTRAINT [DF_StudentWebcastProgress_ProgressPercentage]  DEFAULT ((0)) FOR [ProgressPercentage]
GO
ALTER TABLE [dbo].[Webcast] ADD  CONSTRAINT [DF_Webcast_TimesViewed]  DEFAULT ((0)) FOR [TimesViewed]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment1] FOREIGN KEY([EnrollmentId])
REFERENCES [dbo].[Enrollment] ([EnrollmentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK_Certificate_Enrollment1]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([CourseId])
REFERENCES [dbo].[Course] ([CourseId])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Student1] FOREIGN KEY([StudentMail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Student1]
GO
ALTER TABLE [dbo].[EnrollmentModules]  WITH CHECK ADD  CONSTRAINT [FK_EnrollmentModules_Course] FOREIGN KEY([CourseId])
REFERENCES [dbo].[Course] ([CourseId])
GO
ALTER TABLE [dbo].[EnrollmentModules] CHECK CONSTRAINT [FK_EnrollmentModules_Course]
GO
ALTER TABLE [dbo].[EnrollmentModules]  WITH CHECK ADD  CONSTRAINT [FK_EnrollmentModules_Enrollment] FOREIGN KEY([EnrollmentId])
REFERENCES [dbo].[Enrollment] ([EnrollmentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[EnrollmentModules] CHECK CONSTRAINT [FK_EnrollmentModules_Enrollment]
GO
ALTER TABLE [dbo].[EnrollmentModules]  WITH CHECK ADD  CONSTRAINT [FK_EnrollmentModules_Module] FOREIGN KEY([ContentId], [Title], [Version])
REFERENCES [dbo].[Module] ([ContentId], [Title], [Version])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[EnrollmentModules] CHECK CONSTRAINT [FK_EnrollmentModules_Module]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([CourseId])
REFERENCES [dbo].[Course] ([CourseId])
ON UPDATE CASCADE
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[StudentWebcastProgress]  WITH CHECK ADD  CONSTRAINT [FK_StudentWebcastProgress_Student] FOREIGN KEY([StudentMail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[StudentWebcastProgress] CHECK CONSTRAINT [FK_StudentWebcastProgress_Student]
GO
ALTER TABLE [dbo].[StudentWebcastProgress]  WITH CHECK ADD  CONSTRAINT [FK_StudentWebcastProgress_Webcast] FOREIGN KEY([WebcastContentId])
REFERENCES [dbo].[Webcast] ([ContentId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[StudentWebcastProgress] CHECK CONSTRAINT [FK_StudentWebcastProgress_Webcast]
GO
ALTER TABLE [dbo].[ViewedContent]  WITH CHECK ADD  CONSTRAINT [FK_ViewedContent_Student] FOREIGN KEY([Email])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ViewedContent] CHECK CONSTRAINT [FK_ViewedContent_Student]
GO
ALTER TABLE [dbo].[ViewedWebcasts]  WITH CHECK ADD  CONSTRAINT [FK_ViewedWebcasts_Student] FOREIGN KEY([StudentMail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ViewedWebcasts] CHECK CONSTRAINT [FK_ViewedWebcasts_Student]
GO
