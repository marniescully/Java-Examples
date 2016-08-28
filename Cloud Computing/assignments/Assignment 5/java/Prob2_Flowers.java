
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Renamed Class to Flowers to make it specific to our HW
public class Flowers {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
    		 
    		  // Use Local DB for Problem 2
         .getConnection("jdbc:mysql://localhost/marnie_local_db?" + "user=marnie_local&password=marnie123");
      
      //.getConnection("jdbc:mysql://jelenainst.c5cxb8gzb9wo.us-east-1.rds.amazonaws.com:3306/jaca?" + 
        // "user=jelena&password=jelena123");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from marnie_local_db.FLOWERS");
      System.out.println("The Original Flowers before Insert");
      writeResultSet(resultSet);
      System.out.println("");
      System.out.println("");
      
      // 1st Flower added
      preparedStatement = connect
          .prepareStatement("insert into marnie_local_db.FLOWERS values ( ?, ?, ?)");
      // ("flower_name, height, description");
     
      preparedStatement.setString(1, "Daffodil");
      preparedStatement.setInt(2, 10);
      preparedStatement.setString(3,"Yellow");
      preparedStatement.executeUpdate();
      
   // 2nd Flower added
      preparedStatement = connect
          .prepareStatement("insert into marnie_local_db.FLOWERS values ( ?, ?, ?)");
      // ("flower_name, height, description");
     
      preparedStatement.setString(1, "Mum");
      preparedStatement.setInt(2, 10);
      preparedStatement.setString(3,"Orange");
      preparedStatement.executeUpdate();

      
      // Show the added flowers
      preparedStatement = connect
          .prepareStatement("SELECT flower_name, height, description from marnie_local_db.FLOWERS");
      resultSet = preparedStatement.executeQuery();
      System.out.println("The added flowers");
      writeResultSet(resultSet);
      System.out.println("");
      
      
    } catch (Exception e) {
      throw e;
    } finally {
    	// Close Database connection
      close();
    }

  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String flower_name = resultSet.getString("flower_name");
      Integer height = resultSet.getInt("height");
      String description = resultSet.getString("description");
      System.out.println("Flower Name: " + flower_name);
      System.out.println("Height: " + height);
      System.out.println("Description: " + description);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 