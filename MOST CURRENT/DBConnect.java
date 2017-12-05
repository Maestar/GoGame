/*
File Name: DBConnect.java
Description: Use to handle all the database connection and queries
Authors: Sanjay And Akash

*/

package goGame;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;

class DBConnect { //Sanjay

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Boolean rs1 = false;
    private PreparedStatement pstmt;
    static ArrayList list = new ArrayList(); 
    
    public DBConnect()
    {
        /*
        Author: Sanjay
        Description: used to connect the system with the database
        */
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/godb","root","");
            st= con.createStatement();


            }
        catch(Exception ex){
            System.out.println("Exception: "+ex);
            }
    }
    
    public boolean newUser(String username)
    { 
        /*
        Author: Akash
        Description: used to connect the system with the database
        */
        
        try
            {
            String query= "SELECT * FROM godb where playerName=";
                   query+= "'" + username + "'";// + " and ";

            rs = st.executeQuery(query);
            System.out.println(query);
            if (rs==null){
            return true;
            }
            else return false;
            }
            catch(Exception ex){
                System.out.println("Exception: "+ ex);
                return false;
            }
    }
    
    public boolean checkUser(String username, String password) 
    { 
        /*
        Author: Sanjay
        Description: this function is declared to check the existing users in the system
        */
        
        try
        {
        String query= "SELECT * FROM godb where playerName=";
               query+= "'" + username + "'" + " and ";
               query+= "playerPassword="+"'" + password + "'";
        rs = st.executeQuery(query);
        
        if (rs.first()==true){
        return true;
        }
        else return false;
        }
        catch(Exception ex){
            System.out.println("Exception: "+ ex);
            return false;
        }
        
    
    }
    
    public boolean checkAdmin(String username, String password) 
    { 
        /*
        Author: Akash
        Description: this function is declared to check the existing admin in the system
        */
        
        try
        {
        String query= "SELECT * FROM godb where playerName=";
               query+= "'" + username + "'" + " and ";
               query+= "playerPassword="+"'" + password + "'"+" and isAdmin="+"'"+1+"'";
        rs = st.executeQuery(query);
        
        if (rs.first()==true){
        return true;
        }
        else return false;
        }
        catch(Exception ex){
            System.out.println("Exception: "+ ex);
            return false;
        }
        
    
    }
    
        
    public boolean insertUser(String name, String pass,int isAdmin)
    { 
        /*
        Author: Sanjay
        Description: this function is declared to insert the new user in the database system
        */
        
        try{
            String sql = "INSERT INTO godb(playerName,playerPassword,isAdmin) VALUES(?,?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,pass);
            pstmt.setInt(3,isAdmin);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public ArrayList getData(String name) 
    {    
        /*
        Authors: Sanjay and Akash
        Description: this function is declared to fetch score record the database system
        */
        
        try
        {
            String query = "select distinct score,playerColor,score.playerName from score join godb where score.playerName='"+name+"';";
            rs = st.executeQuery(query);
            
            System.out.println(query);
            System.out.println("Records from Database");
            while(rs.next())
            {
                String a=rs.getString("score");
                String b=rs.getString("playerName");
                if (rs.getBoolean("playerColor")){
                list.add(b+ "   "+a+"  white"); // Akash
                }
                else
                    list.add(a+" black");       //Akash
            }
            return list;
        }
        catch(SQLException ex){
            System.out.println("Exception: "+ ex);
            return null;
        }
        
    }
    
  public void setTimer(int time)
  { 
      /*
        Author: Sanjay
        Description: this function is declared to set timer into the database system
        */
      try
      {
        pstmt=con.prepareStatement("update timer set timer='"+time+"'");
        System.out.println(time);
        int timerUpdated=pstmt.executeUpdate();
        if(timerUpdated == 0){
            System.out.println("NO UPDATED");
        }
        else
            System.out.println("UPDATED data");
      
      }
      catch(Exception e){
          System.out.println(e);
      }
  }
   public int gettimer() {
       /*
        Author: Akash
        Description: this function is declared to get timer value from the database system
        */
       try
       {
       int i;
           String query = "select timer from timer";
       rs= st.executeQuery(query); 
       
       if(rs.next()){
         i = rs.getInt(1);
       
}
       return (rs.getInt(1));
       }
       catch(Exception e)
       {
           System.out.println(e);
           return(-1); 
       }
    }
   
   public void setScore(String player,int score, int color)
   {
       /*
        Author: Sanjay
        Description: this function is declared to set the score value into the database system
        */
       
       try{
            String query="INSERT into score(score,playerName,playerColor) VALUES(?,?,?)";
            
            pstmt=con.prepareStatement(query);
            pstmt.setInt(1,score);
            pstmt.setString(2,player);
            pstmt.setInt(3,color);
            pstmt.executeUpdate();

       }
       catch(Exception e)
       {
           System.out.println(e);
       }
       
   }
    }

