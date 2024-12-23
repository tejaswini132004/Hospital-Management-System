package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Patients(Name,Age,Gender) VALUES(?,?,?)");
            System.out.print("Enter Patient's Name: ");
            String name = scanner.nextLine();
//            scanner.nextLine();
            System.out.print("Enter Patient's Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();


            System.out.print("Enter Patient's Gender: ");
            String gender = scanner.nextLine();

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient's Data entered into Database Successfully");
            } else {
                System.out.println("Unsuccessful! Error while inserting Patient's Data into Database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPatient() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Patients");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+--------+----------------------+----------+-----------+");
            System.out.println("|   ID   |         Name         |    Age   |   Gender  |");
            System.out.println("+--------+----------------------+----------+-----------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-6d | %-20s | %-8d | %-9s |\n",id,name,age,gender);
                System.out.println("+--------+----------------------+----------+-----------+");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
 public boolean getPatientById(int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patients WHERE ID=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     return false;
 }

}
