package Types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Static.Database;

public class Staff {
	
	public String username;
	public String name;
	
	public int group;
	
	public Address address;
	
	public String phone;
	public String mobile;
	
	public boolean sex;		// false = male, true = female.
	public String religion;
	public String birthplace;
	public Date birthday;
	
	public double lembur;
	public double salary;
	public double allowance;
	public double thr;
	public double bonus;
	
	public boolean active;
	public Date employmentDate;
	
	// From database to account object.
	public Staff(String username) {
		String idQuery = String.format("SELECT staffid FROM accounts WHERE username='%s'", username);
		ResultSet reference = Database.query(idQuery);
		String staffId = "";
		try {
			reference.next();
			staffId = reference.getString(1);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		String query = String.format("SELECT * FROM staff WHERE staffid='%s'", staffId);
		ResultSet account = Database.query(query);
		
		try {
			account.next();
			this.username = username;
			this.name = account.getString("name");
			
			this.group = account.getInt("group");
			
			this.address = new Address(
				account.getString("address"),
				account.getString("kelurahan"),
				account.getString("city")
			);
			
			this.phone = account.getString("phone");
			this.mobile = account.getString("mobile");
			
			this.sex = account.getBoolean("sex");
			this.religion = account.getString("religion");
			this.birthplace = account.getString("birthplace");
			this.birthday = account.getDate("birthday");
			
			this.lembur = account.getDouble("lembur");
			this.salary = account.getDouble("salary");
			this.allowance = account.getDouble("allowance");
			this.thr = account.getDouble("thr");
			this.bonus = account.getDouble("bonus");
			
			this.active = account.getBoolean("active");
			this.employmentDate = account.getDate("employmentDate");
		}
		catch(SQLException e) {
			Database.printErrors(e);
		}
	}
}
