package proj;

import java.util.LinkedList;

public interface Connector {
	public void open();
	public String getDate();
	// return >= 1 if succedes, -1 otherwise
	public int insert(String tableName, String... fields);
	// return >= 1 if succedes, -1 otherwise
	public int update(String tableName, String... fields);
	// return >= 1 if succedes, -1 otherwise
	public int delete(String tableName, String... ids);
	public LinkedList<String[]> selectTable(String tableName);
	public LinkedList<String[]> selectTableTask(int taskNumber, String... args);
	public void close();
}