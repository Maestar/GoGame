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
    private PreparedStatement pstmt;
    static ArrayList list = new ArrayList(); 
    
    public DBConnect(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/godb","root","Akash@07");
        st= con.createStatement();
        
        
        }
    catch(Exception ex){
        System.out.println("Exception: "+ex);
        }
    }
    
    public boolean newUser(String username){
    try
        {
        String query= "SELECT * FROM godb where playerName=";
               query+= "'" + username + "'";// + " and ";
               //query+= "playerPassword="+"'" + password + "'";
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
    public boolean checkUser(String username, String password,int mode)
    { 
        
        try
        {
        String query= "SELECT * FROM godb where playerName=";
               query+= "'" + username + "'" + " and ";
               query+= "playerPassword="+"'" + password + "'"+" and isAdmin="+"'"+mode+"'";
        rs = st.executeQuery(query);
        //System.out.println(query);
        System.out.println(rs.first());
        
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
    
        
    public boolean insertUser(String name, String pass,int isAdmin){
    String sql = "INSERT INTO godb(playerName,playerPassword,isAdmin) VALUES(?,?,?)";
 
        try{
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,pass);
            pstmt.setInt(3,isAdmin);
            //pstmt.setString(3,null);
            //pstmt.setString(4,null);
            //pstmt.setString(5,null);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public ArrayList getData(String name){
        String query = "select distinct scoreId,score,playerColor,score.playerName from score join godb where score.playerName='"+name+"';";
            
        try{
            rs = st.executeQuery(query);
            
            
//             ArrayList list = new ArrayList(); 
                        
            System.out.println(query);
            System.out.println("Records from Database");
            while(rs.next()){
                String a=rs.getString("score");
                //list.add(rs.getString("playerPassword"));
                //list.add(rs.getBoolean("isAdmin"));
                String b=rs.getString("playerName");
                if (rs.getBoolean("playerColor")){
                list.add(b+ "   "+a+"  white");
                }
                else
                    list.add(a+" black");
            }
            System.out.println(list);
            
            
        }
        catch(SQLException ex){
            System.out.println("Exception: "+ ex);
        }
        return list;
    }
    
  public void setTimer(int time){
      try{
      String query="update timer set timer='"+time+"'";
      rs = st.executeQuery(query);
      }
      catch(Exception e){
          System.out.println(e);
      }
  }  
    }

