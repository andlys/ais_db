package proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.HashMap;

public class DBConnector implements Connector {
	private Connection connection = null;
    private final String driverName = "org.sqlite.JDBC";
    private final String connectionString = "jdbc:sqlite:database.db";
	private final HashMap<String, String> 	mapInsert = new HashMap<>(),
											mapUpdate = new HashMap<>(),
											mapDelete = new HashMap<>();

	DBConnector() {
		mapInsert.put("client", "INSERT INTO client (`phone`, `last_name`, `first_name`, `middle_name`) VALUES ('%s', '%s', '%s', '%s')");
		mapInsert.put("transport", "INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')");
		mapInsert.put("department", "INSERT INTO department (`city`, `street`, `building`, `opens_at_hour`, `closes_at_hour`) VALUES ('%s', '%s', '%s', '%s', '%s')");
		mapInsert.put("department_phone", "INSERT INTO department_phone (`phone`, `department_id`) VALUES ('%s', '%s')");
		mapInsert.put("driver", "INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')");
		mapInsert.put("clerk", "INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')");
		mapInsert.put("shipment", "INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', 1)"); // last value '1' is hard-coded!!!
		mapInsert.put("location", "INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('%s', '%s', '%s', '%s')");
		
		mapUpdate.put("client", "UPDATE client SET `phone` = '%s', `last_name` = '%s', `first_name` = '%s', `middle_name` = '%s' WHERE `client_id` = '%s'");
		mapUpdate.put("transport", "UPDATE transport SET `model_name` = '%s', `km_run` = '%s', `volume_capacity` = '%s', `weight_capacity` = '%s', `fuel_consumption` = '%s', `last_inspection_date` = '%s' WHERE transport_id = '%s'");
		mapUpdate.put("department", "UPDATE department SET `city` = '%s', `street` = '%s', `building` = '%s', `opens_at_hour` = '%s', `closes_at_hour` = '%s' WHERE department_id = '%s'");
		mapUpdate.put("department_phone", "UPDATE department_phone SET `phone` = '%s', `department_id` = '%s' WHERE department_phone_id = '%s'");
		mapUpdate.put("driver", "UPDATE driver SET `phone` = '%s', `last_name` = '%s', `first_name` = '%s', `middle_name` = '%s', `works_since_date` = '%s', `salary` = '%s', `transport_id` = '%s' WHERE driver_id = '%s'");
		mapUpdate.put("clerk", "UPDATE clerk SET `phone` = '%s', `last_name` = '%s', `first_name` = '%s', `middle_name` = '%s', `works_since_date` = '%s', `salary` = '%s', `department_id` = '%s' WHERE clerk_id = '%s'");
		mapUpdate.put("shipment", "UPDATE shipment SET `registered_at_stamp` = '%s', `client_paid` = '%s', `from_department_id` = '%s', `to_department_id` = '%s', `sender_client_id` = '%s', `receiver_client_id` = '%s', `registered_clerk_id` = '%s', `shipment_type_id` = '%s' WHERE shipment_id = '%s'");
		mapUpdate.put("location", "UPDATE location SET `delivered_at_stamp` = '%s', `shipment_id` = '%s', `department_id` = '%s', `driver_id` = '%s' WHERE location_id = '%s'");
		
		mapDelete.put("client", "DELETE FROM client WHERE `client_id` = '%s'");
		mapDelete.put("transport", "DELETE FROM transport WHERE `transport_id` = '%s'");
		mapDelete.put("department", "DELETE FROM department WHERE `department_id` = '%s'");
		mapDelete.put("department_phone", "DELETE FROM department_phone WHERE `department_phone_id` = '%s'");
		mapDelete.put("driver", "DELETE FROM driver WHERE `driver_id` = '%s'");
		mapDelete.put("clerk", "DELETE FROM clerk WHERE `clerk_id` = '%s'");
		mapDelete.put("shipment", "DELETE FROM shipment WHERE `shipment_id` = '%s'");
		mapDelete.put("location", "DELETE FROM location WHERE `location_id` = '%s'");
	}
	
    public void open() {
        try {
            Class.forName(driverName);
			connection = DriverManager.getConnection(connectionString);
			System.out.println("DBConnection opened!");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't get class. No driver found");
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
        }
	}
	
	public void close() {
        try {
            connection.close();
			System.out.println("DBConnection closed!");
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }
    }
	
	public String getDate() {
		String result = null;
		try {
			try (Statement st = connection.createStatement()) {
				ResultSet rs = st.executeQuery("SELECT datetime('now', '+3 hour')");
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	public LinkedList<String[]> selectTable(String tableName) {
		LinkedList<String[]> res = new LinkedList<>();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
			final int columnsCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String[] arr = new String[columnsCount];
				int i = 1;
				while (i <= columnsCount)
						arr[i-1] = rs.getString(i++);
				res.add( arr );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return res;
		}
	}
	
	public LinkedList<String[]> selectTableTask(int taskNumber, String... args) {
		String[] queries = {"SELECT cs.client_id,\n" +
							"       cs.last_name,\n" +
							"       cs.first_name,\n" +
							"       cs.middle_name,\n" +
							"       cs.shipment_id,\n" +
							"\t   cs.receiver_client_id,\n" +
							"       location.delivered_at_stamp,\n" +
							"       location.location_id,\n" +
							"       location.department_id\n" +
							"FROM (client\n" +
							"      INNER JOIN shipment ON client.client_id = shipment.sender_client_id) cs\n" +
							"INNER JOIN LOCATION ON location.shipment_id = cs.shipment_id\n" +
							"ORDER BY cs.client_id,\n" +
							"         cs.last_name,\n" +
							"         cs.first_name,\n" +
							"         cs.middle_name,\n" +
							"         cs.shipment_id,\n" +
							"\t\t cs.receiver_client_id,\n" +
							"         location.delivered_at_stamp,\n" +
							"         location.location_id,\n" +
							"         location.department_id ASC;",

							"SELECT clerk_avg_salary,\n" +
									"       driver_avg_salary,\n" +
									"       ABS(clerk_avg_salary - driver_avg_salary) AS avg_salary_diff,\n" +
									"\t   clerk_sum_salary,\n" +
									"\t   driver_sum_salary,\n" +
									"\t   clerk_sum_salary + driver_sum_salary AS total_sum_salary,\n" +
									"\t   client_paid_sum\n" +
									"FROM\n" +
									"  (SELECT AVG(salary) AS clerk_avg_salary, SUM(salary) AS clerk_sum_salary\n" +
									"   FROM clerk),\n" +
									"  (SELECT AVG(salary) AS driver_avg_salary, SUM(salary) AS driver_sum_salary\n" +
									"   FROM driver),\n" +
									"  (SELECT SUM(client_paid) AS client_paid_sum\n" +
									"   FROM shipment);",

							"SELECT department.department_id,\n" +
									"       department.city,\n" +
									"       AVG(clerk.salary)\n" +
									"FROM department\n" +
									"INNER JOIN clerk ON department.department_id = clerk.department_id\n" +
									"GROUP BY department.department_id,\n" +
									"         department.city;",

							"SELECT clerk.clerk_id,\n" +
									"       clerk.last_name,\n" +
									"       clerk.first_name,\n" +
									"       clerk.middle_name,\n" +
									"       COUNT(shipment.shipment_id) AS number_of_shipments\n" +
									"FROM shipment\n" +
									"INNER JOIN clerk ON shipment.registered_clerk_id = clerk.clerk_id\n" +
									"GROUP BY clerk.clerk_id,\n" +
									"         clerk.last_name,\n" +
									"         clerk.first_name,\n" +
									"         clerk.middle_name\n" +
									"ORDER BY number_of_shipments DESC;",

							"SELECT *\n" +
									"FROM shipment\n" +
									"WHERE receiver_client_id =\n" +
									"    (SELECT client_id\n" +
									"     FROM client\n" +
									"     WHERE phone = \"" + args[0] + "\")\n" +
									"ORDER BY shipment.registered_at_stamp ASC;"};
		LinkedList<String[]> res = new LinkedList<>();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery( queries[taskNumber-1] );
			final int columnsCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String[] arr = new String[columnsCount];
				int i = 1;
				while (i <= columnsCount)
						arr[i-1] = rs.getString(i++);
				res.add( arr );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return res;
		}
	}
	
	public int insert(String tableName, String... fields) {
		int status = -1;
		try (Statement st = connection.createStatement()) {
			String query = mapInsert.get(tableName);
			status = st.executeUpdate(String.format(query, fields));
			System.out.println("insertion status: " + status);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return status;
		}
	}
	
	public int update(String tableName, String... fields) {
		int status = -1;
		try (Statement st = connection.createStatement()) {
			String query = mapUpdate.get(tableName);
			status = st.executeUpdate(String.format(query, fields));
			System.out.println("update status: " + status);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return status;
		}
	}
	
	public int delete(String tableName, String... ids) {
		int status = -1;
		try (Statement st = connection.createStatement()) {
			String query = mapDelete.get(tableName);
			status = st.executeUpdate(String.format(query, ids));
			System.out.println("delete status: " + status);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return status;
		}
	}
	
}