package Backend;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Omar
 */
public class User {

    private String userID;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String email;
    private boolean status;

    private ArrayList<User> friends;

    public User(String username, String password, LocalDate dateOfBirth, String email) {
        this.username = username;
        this.password = hashPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
 

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
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
        } catch (Exception e) {
            System.out.println("Could not hash password ");
            return null;
        }

    }
        
    
   
    public String generateUserID() {
       

        // Define the characters allowed in the user ID
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder userId = new StringBuilder();

        // Generate random characters for the user ID
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(chars.length());
            userId.append(chars.charAt(randomIndex));
        }

        return userId.toString();
    }
    
    @Override
    public String toString(){
       
        return this.userID+","+ this.username+","+ this.email+","+ this.password+","+ this.dateOfBirth+","+this.status;
    }
}