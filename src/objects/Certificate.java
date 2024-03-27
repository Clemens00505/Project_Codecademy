package objects;

import database.DatabaseConnection;

public class Certificate {
    private int enrollmentId;
    private int certificateId;
    private String studentMail;

    public Certificate() {

    }

    public Certificate(String studentMail, int enrollmentId) {
        this.studentMail = studentMail;
        this.enrollmentId = enrollmentId;
    }

    

    public void insertIntoDatabase(Certificate certificate, DatabaseConnection databaseConnection) {
        StringBuilder insertStmt = new StringBuilder();

        insertStmt.append("INSERT INTO Certificate (StudentMail, EnrollmentId) VALUES ('");
        insertStmt.append(certificate.getStudentMail() + "', ");
        insertStmt.append(certificate.getEnrollmentId() + ")");

        databaseConnection.openConnection();
        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public String getStudentMail() {
        return studentMail;
    }

}
