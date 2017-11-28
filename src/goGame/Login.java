/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goGame;


public class Login {
    public static String username;
    public static String password; 
    
    DBConnect database= new DBConnect();
    
    public static boolean validateUser(String user1,String password1,String user2,String password2)
    {
       // boolean  user1 = database.checkUser(user1,password1);

        
        return false;
    }
}
