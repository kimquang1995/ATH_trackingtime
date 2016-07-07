import java.sql.*;

public class testConnect_DB  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnection db = new DatabaseConnection();		
		try {
			db.Connect();
			PreparedStatement query = db.getConnection().prepareStatement("Select * from Tag");
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
			System.out.println(resultSet.getString("Name"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
