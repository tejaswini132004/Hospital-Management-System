package HospitalManagementSystem;

import javax.management.relation.RelationSupport;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "admin";

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);

            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1: Add Patient");
                System.out.println("2: View Patient");
                System.out.println("3: View Doctors");
                System.out.println("4: Book Appointment");
                System.out.println("5: Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        patient.addPatient();

                        System.out.println();
                        break;
                    case 2:
                        scanner.nextLine();
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointments(connection, scanner, patient,doctor);
                        System.out.println();
                        break;
                    case 5:
                        return;

                    default:
                        System.out.println("Enter valid choice");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void bookAppointments(Connection connection, Scanner scanner, Patient patient, Doctor doctor) {
        System.out.print("Enter Patient Id: ");
        int patient_id = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctor_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Appointment Date in the format of (YYYY-MM-DD): ");
        String appointment_date = scanner.nextLine();
        if (patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)) {
            if (checkDoctorAvailability(doctor_id,appointment_date,connection )) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO appointments(Patient_id,Doctor_id,Appointment_Date) VALUES(?,?,?)");
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment booked Successfully");
                    }else {
                        System.out.println("Appointment Not Booked");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Doctor is not available");
            }
        } else {
            System.out.println("Neither doctor nor patient available");
        }

    }
    public static boolean checkDoctorAvailability(int doctor_id, String appointment_date,Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM appointments WHERE Doctor_id=? AND Appointment_Date=?");
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointment_date);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count=resultSet.getInt(1);
                        if(count==0){
                            return true;
                        }else {
                            return false;
                        }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  false;
    }
}
