package proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.HashMap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Main {
	final static Connector connector = new DBConnector(); //todo: rename
	private static String[] clientColumns = new String[] {"id", "Телефон", "Прізвище", "Ім'я", "По-батькові"},
							clerkColumns  = new String[] {"id", "Телефон", "Прізвище", "Ім'я", "По-батькові", "Працює з", "Зарплата", "Номер відділення"},
							driverColumns = new String[] {"id", "Телефон", "Прізвище", "Ім'я", "По-батькові", "Працює з", "Зарплата", "Номер транспорту"},
							transportColumns = new String[] {"id", "Модель", "Пробіг(км)", "Об'єм(м3)", "Макс. вантаж(кг)", "Споживання пального (л/100км)", "Останній техогляд"},
							departmentColumns = new String[] {"id", "Місто", "Вулиця", "Будинок", "Відкривається о (година)", "Закривається о (година)", "Табельний номер менеджера"},
							locationColumns = new String[] {"id", "Доставлено о (дата)", "Відправлення (номер)", "Доставлено у відділення (номер)", "Доставив водій (номер)"},
							shipmentColumns = new String[] {"id", "Зареєстровано о", "Клієнт заплатив (грн)", "Від відділення", "До відділення", "Клієнт відправник", "Клієнт одержувач", "Зареєстрував клерк", "Тип відправлення"},
							task1Columns = new String[] {"Відправник", "Прізвище", "Ім'я", "По-батькові", "Відправлення", "Одержувач", "Доставлено о", "Місцезнаходження", "Відділення"},
							task2Columns = new String[] {"Середня з/п клерків", "Середня з/п водіїв", "Різниця середніх з/п", "Сума з/п клерків", "Сума з/п водіїв", "Загальна сума з/п", "Всього сплачено клієнтами"},
							task3Columns = new String[] {"Номер відділення", "Місто відділення", "Середня з/п клерків по відділенню"},
							task4Columns = new String[] {"Номер клерка", "Прізвище", "Ім'я", "По-батькові", "Зареєстровано відправлень"},
							task5Columns = new String[] {"id", "Зареєстровано о", "Клієнт заплатив (грн)", "Від відділення", "До відділення", "Клієнт відправник", "Клієнт одержувач", "Зареєстрував клерк", "Тип відправлення"};
	
	
    public static void main(String[] args)
    {
        connector.open();
		System.out.println( "Last update: " + connector.getDate() );
		System.out.println( "Retrieving data from select statement..." );
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(4, 3));
		JButton btn1 = new JButton("Клієнти");
		btn1.addActionListener((e) -> showTable(clientColumns, "client", "Клієнти"));
		JButton btn2 = new JButton("Клерки");
		btn2.addActionListener((e) -> showTable(clerkColumns, "clerk", "Клерки"));
		JButton btn3 = new JButton("Водії");
		btn3.addActionListener((e) -> showTable(driverColumns, "driver", "Водії"));
		JButton btn4 = new JButton("Транспортні засоби");
		btn4.addActionListener((e) -> showTable(transportColumns, "transport", "Транспортні засоби"));
		JButton btn5 = new JButton("Поштові відділення");
		btn5.addActionListener((e) -> showTable(departmentColumns, "department", "Поштові відділення"));
		JButton btn6 = new JButton("Місцезнаходження");
		btn6.addActionListener((e) -> showTable(locationColumns, "location", "Місцезнаходження"));
		JButton btn7 = new JButton("Відправлення");
		btn7.addActionListener((e) -> showTable(shipmentColumns, "shipment", "Відправлення"));
		JButton btn8 = new JButton("Місцезнаходження відправлень клієнтів");
		btn8.addActionListener((e) -> showTableTask(task1Columns, 1, "Місцезнаходження відправлень клієнтів"));
		JButton btn9 = new JButton("Статистика фінансів");
		btn9.addActionListener((e) -> showTableTask(task2Columns, 2, "Статистика фінансів"));
		JButton btn10 = new JButton("Середня з/п клерків по відділеннях");
		btn10.addActionListener((e) -> showTableTask(task3Columns, 3, "Середня з/п клерків по відділеннях"));
		JButton btn11 = new JButton("Найпрацьовитіші клерки");
		btn11.addActionListener((e) -> showTableTask(task4Columns, 4, "Найпрацьовитіші клерки"));
		JButton btn12 = new JButton("Відправлення за телефоном отримувача");
		btn12.addActionListener((e) -> showTableTask(task5Columns, 5, "Відправлення за телефоном отримувача"));
		btnPanel.add( btn1 );
		btnPanel.add( btn2 );
		btnPanel.add( btn3 );
		btnPanel.add( btn4 );
		btnPanel.add( btn5 );
		btnPanel.add( btn6 );
		btnPanel.add( btn7 );
		btnPanel.add( btn8 );
		btnPanel.add( btn9 );
		btnPanel.add( btn10 );
		btnPanel.add( btn11 );
		btnPanel.add( btn12 );
		
        JFrame jf = new JFrame("Автоматизована інформаційна система");
        jf.setSize(850, 180);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		//JLabel timeLabel = new JLabel(String.format("Last update:    %s", connector.getDate()), SwingConstants.RIGHT); // todo: consider deleting or replacing this...
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		//container.add(timeLabel, BorderLayout.SOUTH);
		container.add(new JLabel("Програмний застосунок поштової служби \"Modern Post\"", SwingConstants.CENTER), BorderLayout.NORTH);
		container.add(btnPanel, BorderLayout.CENTER);
		jf.add(container);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.out.println("Window closed! Closing DBConnection as well!");
				connector.close(); // todo: swingutils.invoke later... ???
			}
		});
    }
	
	private static void showTable(String[] columns, String tableName, String guiTableName) {
		//System.out.println("clicked btn");
		JFrame frame = new JFrame("Таблиця " + "\"" + guiTableName + "\"");
        frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		MyPanel panel = new MyPanel(columns, connector.selectTable(tableName), tableName, true);
		frame.add(panel);
	}
	
	private static void showTableTask(String[] columns, int taskNumber, String guiTableName) {
		//System.out.println("clicked btn");
		String phone = null;
		if (taskNumber == 5) {
			phone = (String)JOptionPane.showInputDialog(
								null,
								"Отримати всі відправлення за\n"
								+ "телефоном отримувача",
								"Введіть номер телефону",
								JOptionPane.INFORMATION_MESSAGE,
								null,
								null,
								"067-777-77-75");
			if (phone == null || phone.length() == 0) {
				JOptionPane.showMessageDialog(null,
					"Ви не ввели номер телефону!",
					"Помилка",
					JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		System.out.println(phone);
		JFrame frame = new JFrame("Таблиця " + "\"" + guiTableName + "\"");
        frame.setSize(1050, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		MyPanel panel = new MyPanel(columns, connector.selectTableTask(taskNumber, phone), null, false);
		frame.add(panel);
	}
}