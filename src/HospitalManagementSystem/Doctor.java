package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;

    }

    public void viewDoctor() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Doctors");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+--------+----------------------+----------------------+");
            System.out.println("|   ID   |         Name         |     Specialization   |");
            System.out.println("+--------+----------------------+----------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String specialization=resultSet.getString("Specialization");
                System.out.printf("| %-6d | %-20s | %-20s |\n",id,name,specialization);
                System.out.println("+--------+----------------------+----------------------+");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public boolean getDoctorById(int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctors WHERE ID=?");
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
