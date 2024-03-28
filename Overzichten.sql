--Voor een geselecteerd account, geef welke certificaten behaald zijn:
SELECT *
FROM Certificate
WHERE StudentMail = '';

--Geef een top 3 van meest bekeken webcasts:
SELECT TOP(3) *
FROM Webcast
ORDER BY TimesViewed DESC;
