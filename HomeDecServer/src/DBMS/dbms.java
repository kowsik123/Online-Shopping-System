package DBMS;

import java.sql.*;
import java.util.ArrayList;

public class dbms {
	Connection conn;
	Statement st;
	public dbms(String userName,String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",userName,password);
			st=conn.createStatement();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Object[][] getResult(String s,int col) {
		ResultSet res=queryWithResult(s);
		ArrayList<Object>[] arr=new ArrayList[col];
		for(int i=0;i<col;i++) {
			arr[i]=new ArrayList<Object>();
		}
		try {
			while(res.next()){
				for(int i=0;i<col;i++) {
					arr[i].add(res.getObject(i+1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] table =new Object[arr[0].size()][col];
		for(int j=0;j<col;j++) {
			int ind=0;
			for(Object i: arr[j]) {
				table[ind++][j]=i;
			}
		}
		return table;
	}
	public ArrayList<String> getColumn(String s) {
		ArrayList<String> arr=new ArrayList<String>();
		Object[][] table=getResult(s,1);
		for(int i=0;i<table.length;i++) {
			arr.add(table[i][0].toString());
		}
		return arr;
	}
	public Object[] getCol(String s) {
		ArrayList<Object> arr=new ArrayList<Object>();
		Object[][] table=getResult(s,1);
		for(int i=0;i<table.length;i++) {
			arr.add(table[i][0]);
		}
		return arr.toArray();
	}
	public boolean query(String s) {
		try {
			st.executeUpdate(s);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ResultSet queryWithResult(String s) {
		try {
			return st.executeQuery(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public PreparedStatement getPS(String s) {
		try {
			return conn.prepareStatement(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Object getValue(String s) {
		ResultSet rs=queryWithResult(s);
		try {
			if(rs.next()) {
				return rs.getObject(1); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void deleteAll() {
		String s="BEGIN\r\n"
				+ "   FOR cur_rec IN (SELECT object_name, object_type\r\n"
				+ "                   FROM user_objects\r\n"
				+ "                   WHERE object_type IN\r\n"
				+ "                             ('TABLE',\r\n"
				+ "                              'VIEW',\r\n"
				+ "                              'MATERIALIZED VIEW',\r\n"
				+ "                              'PACKAGE',\r\n"
				+ "                              'PROCEDURE',\r\n"
				+ "                              'FUNCTION',\r\n"
				+ "                              'SEQUENCE',\r\n"
				+ "                              'SYNONYM',\r\n"
				+ "                              'PACKAGE BODY'\r\n"
				+ "                             ))\r\n"
				+ "   LOOP\r\n"
				+ "      BEGIN\r\n"
				+ "         IF cur_rec.object_type = 'TABLE'\r\n"
				+ "         THEN\r\n"
				+ "            EXECUTE IMMEDIATE 'DROP '\r\n"
				+ "                              || cur_rec.object_type\r\n"
				+ "                              || ' \"'\r\n"
				+ "                              || cur_rec.object_name\r\n"
				+ "                              || '\" CASCADE CONSTRAINTS';\r\n"
				+ "         ELSE\r\n"
				+ "            EXECUTE IMMEDIATE 'DROP '\r\n"
				+ "                              || cur_rec.object_type\r\n"
				+ "                              || ' \"'\r\n"
				+ "                              || cur_rec.object_name\r\n"
				+ "                              || '\"';\r\n"
				+ "         END IF;\r\n"
				+ "      EXCEPTION\r\n"
				+ "         WHEN OTHERS\r\n"
				+ "         THEN\r\n"
				+ "            DBMS_OUTPUT.put_line ('FAILED: DROP '\r\n"
				+ "                                  || cur_rec.object_type\r\n"
				+ "                                  || ' \"'\r\n"
				+ "                                  || cur_rec.object_name\r\n"
				+ "                                  || '\"'\r\n"
				+ "                                 );\r\n"
				+ "      END;\r\n"
				+ "   END LOOP;\r\n"
				+ "   FOR cur_rec IN (SELECT * \r\n"
				+ "                   FROM all_synonyms \r\n"
				+ "                   WHERE table_owner IN (SELECT USER FROM dual))\r\n"
				+ "   LOOP\r\n"
				+ "      BEGIN\r\n"
				+ "         EXECUTE IMMEDIATE 'DROP PUBLIC SYNONYM ' || cur_rec.synonym_name;\r\n"
				+ "      END;\r\n"
				+ "   END LOOP;\r\n"
				+ "END;\r\n"
				+ "/";
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		new OrderTable().createTable();
		new ProductTable().createTable();
		new UserTable().createTable();
	}
}
