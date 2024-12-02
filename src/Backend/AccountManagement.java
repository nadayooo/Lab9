package Backend;

import Constants.FileNames;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP
 */
public class AccountManagement implements FileNames {

    private ArrayList<User> users;
    private static String filename;

    public AccountManagement() {
        this.users = readFromFile();

    }

    public static ArrayList<User> readFromFile() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USER_NAMES);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println("The file was not found!");
            }
        }
        try (Scanner in = new Scanner(file)) {

            while (in.hasNextLine()) {
                String line = in.nextLine();
                User user = createUser(line);
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found!");
        }
        return users;
    }

    public boolean insertUser(User u) {
        for (User user : users) {
            if (user.getUserID().equals(u.getUserID())) {
                System.out.println("User already exists.");
                return false;
            }
        }
        users.add(u);
        System.out.println("Successfully Added!");
        return true;
    }
    
    public boolean deleteUser(User u)
    {
        for(User user:users){
             if (user.getUserID().equals(u.getUserID())) {
                 users.remove(u);
                System.out.println("Successfully Deleted.");
                return true;
            }
        }
        System.out.println("User Not Found!");
        return false;
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
//Overloading to search for the user during signup and login

    //login
    public User searchUser(String id, String password) {
        String hashedPassword = hashPassword(password);
        for (User u : users) {
            if (u.getUserID().equals(id) && u.getPassword().equals(hashedPassword)) {
                return u;
            }
        }
        return null;
    }

    //signup
    public boolean searchEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static User createUser(String line) {
        String[] token = line.split(",");
        String username = token[1];
        String password = token[2];
        LocalDate dateOfBirth = LocalDate.parse(token[3]);
        String email = token[4];
        return new User(username, password, dateOfBirth, email);
    }

    private String hashPassword(String password) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password and convert it to bytes
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not hash password ");
            return null;
        }

    }

}
