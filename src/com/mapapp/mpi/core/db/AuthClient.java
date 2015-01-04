package com.mapapp.mpi.core.db;

import com.mapapp.mpi.core.db.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ganesh Ravendranathan
 */
public class AuthClient extends Thread{

    User activeUser;

    /**
     * Creates a constant connection between the server and the user's profile on the device.
     * Used for logging in to services.
     *
     */
    public AuthClient(User activeUser){
        this.activeUser = activeUser;
    }

    public void run(){
        while (activeUser.verifies());
    }


    public void doSomething(){
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection connection = null;

            connection = DriverManager.getConnection("jdbc:mysql://localhost/users", "root", "a7w282");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists users");
            statement.executeUpdate("CREATE TABLE users (alias varchar(15), first_name varchar(20), " +
                    "last_name varchar(20), email varchar(255), password varchar(255))");

            statement.executeUpdate("insert into users values ('G32', 'Ganesh', 'Ravendranathan', 'ganesh_8@msn.com', 'pass32')");
            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next())
            {
                // read the result set
                System.out.println("Alias = " + rs.getString("alias") + " FirstName = " + rs.getString("first_name") + " LastName = " + rs.getString("last_name") + " Email = " + rs.getString("email") + " Password = " +  rs.getString("password"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
