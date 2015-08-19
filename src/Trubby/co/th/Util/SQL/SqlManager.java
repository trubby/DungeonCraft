package Trubby.co.th.Util.SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import Trubby.co.th.DG;

public class SqlManager {

	//public MySQL sql = new MySQL(GTA.instance, "localhost", "3306", "MC", "root", "");
	public MySQL sql = new MySQL(DG.plugin, "localhost", "3306", "MC", "root", "46502");
	public Connection connection = null;
	
	public void createTable(){
		Bukkit.broadcastMessage("1");
		//Create table
		try {
			Statement statement = connection.createStatement();
			//statement.executeUpdate("CREATE TABLE GTA(Name varchar(16),Kill int,Death int);");
			statement.executeUpdate("CREATE TABLE  `GTA` (`Name` VARCHAR( 16 ) NOT NULL ,`Kill` INT NOT NULL ,`Death` INT NOT NULL) ENGINE = MYISAM ;");
			statement.close();
			Bukkit.broadcastMessage("2");
		} catch (SQLException e) {
			System.out.println("[GTA]table already exist.");
			Bukkit.broadcastMessage("3");
		}
	}
	
	public void openConnection(){
		try {
			connection = sql.openConnection();
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
	
	public void closeConnection(){
		try {
			sql.closeConnection();
		} catch (SQLException e) {
		}
	}
	
}
