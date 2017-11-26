package goGame;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Sanjay
 */
class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Boolean rs1 = false;
    
    static ArrayList list = new ArrayList(); 
    
    public DBConnect(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/godb","root","");
        st= con.createStatement();
        
        
        }
    catch(Exception ex){
        System.out.println("Exception: "+ex);
        }
    }
    
    
    public void getData(){
        try{
            String query = "select * from godb where playerName='sanjay12'";
            rs = st.executeQuery(query);
            
            
//             ArrayList list = new ArrayList(); 
                        
            
            System.out.println("Records from Database");
            while(rs.next()){
                list.add(rs.getString("playerId"));
                list.add(rs.getString("playerName"));
                list.add(rs.getString("playerPassword"));
                list.add(rs.getBoolean("isAdmin"));
                list.add(rs.getBoolean("playerColor"));
                
//                String playerName = rs.getString("playerName");
//                String playerPassword = rs.getString("playerPassword");
//                Boolean isAdmin = rs.getBoolean("isAdmin");
//                Boolean playerColor = rs.getBoolean("playerColor");
//                int timer = rs.getInt("timer");
//                
//                System.out.println("playerName: "+playerName+ " playerPassword: "+playerPassword+ 
//                        " isAdmin "+isAdmin+ " playerColor:"+playerColor+ " timer "+timer);
            }
            
            
            
        }
        catch(Exception ex){
            System.out.println("Exception: "+ ex);
        }
    }
    
    public boolean login(String username, String password){
        try{
        String query = "select * from godb where ";
        query+= "playerName="+"'"+username+"' and playerPassword="+"'"+password+"'";
        rs = st.executeQuery(query);
        System.out.println(rs);
           return false; }
        catch(Exception ex){System.out.println(ex);
            return false;}
            
    }
}
