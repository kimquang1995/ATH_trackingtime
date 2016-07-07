import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Thanh Huy on 7/1/2016.
 */
public class DatabaseConnection {
    private String ip, db, un, pass;
    private Connection connection;
    public DatabaseConnection() {
        //ip = "135.234.238.136";
        ip = "cmu.vanlanguni.edu.vn";
        db = "nghianguyen";
        un = "nghianguyen";
        pass = "t133981";
    }
    public void Connect() throws Exception {
        connection = null;
        String S_url = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            S_url = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + pass + ";";
            connection = (Connection) DriverManager.getConnection(S_url);
            System.out.println("Thanh cong");
        } catch (ClassNotFoundException e) {
           System.out.println("Khong tim thay Class");
        } catch (SQLException e) {
        	 System.out.println("Khong tim thay SQL");
        }
    
    }
    public Connection getConnection()
    {
        return connection;
    }
}

