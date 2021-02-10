package TODO.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	public Connection connection=  null;
	public PreparedStatement prestatement = null;
	public ResultSet resultSet = null;
	private final static String URL = "jdbc:mysql://localhost/todolist?useUnicode=true&use"
			+ "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public Database(){
		try {
			connection = DriverManager.getConnection(URL,"andu","password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
