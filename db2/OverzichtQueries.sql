--1: Voor een geselecteerd geslacht, geef aan voor hoeveel procent van de cursussen waarvoor ingeschreven is, het certificaat behaald is.
SELECT
	(SELECT COUNT(*)
	FROM Enrollment
	WHERE Percentage = 100 AND StudentMail in (SELECT Email	
							FROM Student
							WHERE Gender = 'man')
							) * 1.0 / (SELECT COUNT(*)
										FROM Enrollment)

--2: Voor een geselecteerde cursus, geef per module de gemiddelde voortgang in percentage van de totale lengte, voor alle accounts(Voer CourseId in)
SELECT Module.Title, Module.Version, EnrollmentModules.AverageProgress 
FROM Module 
JOIN (
    SELECT ContentId, AVG(CAST(Progress AS FLOAT)) AS AverageProgress 
    FROM EnrollmentModules 
    WHERE CourseId = 1
    GROUP BY ContentId
) AS EnrollmentModules ON Module.ContentId = EnrollmentModules.ContentId 
WHERE Module.CourseId = 1;


--3: Voor een geselecteerd account en geselecteerde cursus, geef per module de voortgang als percentage(Voor EnrollmentId in).
SELECT * 
FROM EnrollmentModules 
WHERE EnrollmentId = 9

--4: Voor een geselecteerd account, geef welke certificaten behaald zijn(voer emailadres in):
SELECT *
FROM Certificate
WHERE StudentMail = 'clemens@gmail.com';

--5: Geef een top 3 van meest bekeken webcasts:
SELECT TOP(3) *
FROM Webcast
ORDER BY TimesViewed DESC;

--8: Voor een geselecteerde cursus, geef hoeveel cursisten deze in het geheel behaald hebben(Voor courseId in):
SELECT COUNT(*) AS amountCompleted 
FROM Certificate 
WHERE EnrollmentId IN (SELECT EnrollmentId 
						FROM Enrollment 
						WHERE CourseId = 1);


