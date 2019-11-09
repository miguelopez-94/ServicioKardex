package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.microsoft.sqlserver.*;
import java.util.ArrayList;

public class DAO {
	
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String connectionString = "jdbc:sqlserver://DESKTOP-Q1D8IK4\\SAPDESARROLLO; databaseName=Kardex; user=sa;password=s31d0r2019;";

	public Connection cnt;
	
	public void connection() 
	{
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(connectionString);
			cnt = DriverManager.getConnection(connectionString);
		} catch (Exception e) {
			System.err.println("Connection Failed. User/Passwd Error? Message: " + e.getMessage());
			return;
		}
		if (connection != null) {
			try {
				System.out.println("Connection to SQLServer successful!");
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(" select Count(*) as [rowcount] from [dbo].[User] ");
				resultSet.next();
				int hello = resultSet.getInt("rowcount");				//String hello = resultSet.getString(1);
				resultSet.close();
				System.out.println(""+ hello +"");
			} catch (SQLException e) {
				System.err.println("Query failed!");
			}
		}
		
	}
	
	

	public ArrayList<Item> obtenerProductos() throws Exception
	{
		try	
		{
			
			ArrayList<Item> n = new ArrayList<Item>();
			Item nuevo = new Item();
			Statement stmt = cnt.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from Reg_Product ");
			while (resultSet.next()) 
			{
					nuevo = new Item();
					nuevo.setId(resultSet.getLong("Id_Product"));
					nuevo.setValue(resultSet.getString("Nom_Product"));
					n.add(nuevo);
			}
			resultSet.close();
			
			
			
		return n;
		}
		catch(Exception Ex) 
		{
			throw Ex;
		}
		
	}

	public Item ConsultarProducto(String Id) throws Exception
	{
		try	
		{
			Item nuevo = new Item();
			Statement stmt = cnt.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from Reg_Product where Nom_Product = '" + Id +"' ");
			while (resultSet.next()) 
			{
				nuevo = new Item();
				nuevo.setId(resultSet.getLong("Id_Product"));
				nuevo.setValue(resultSet.getString("Nom_Product"));
			}
			resultSet.close();
		return nuevo;
		}
		catch(Exception Ex) 
		{
			throw Ex;
		}	
	}
	
	
	public long ConsultarCantidad(String Id) throws Exception
	{
		try	
		{
			long valor = 0;
			Statement stmt = cnt.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from Reg_Product where Id_Product = '" + Id +"' ");
			while (resultSet.next()) 
			{
				valor = resultSet.getLong("cant_Product");

			}
			resultSet.close();
		return valor;
		}
		catch(Exception Ex) 
		{
			throw Ex;
		}
		
		
	}
	
	
	public String InsertarVenta(String _Id,String  _cant) 
	{
		try 
		{
			Item n = new Item();
			n = ConsultarProducto(_Id);
			long value = ConsultarCantidad("" +n.getId()+ "");
			
			if(Long.parseLong(_cant)>value) 
			{
				return "el valor del producto excede la cantidad en el inventario";
				
			}
			Statement stmt = cnt.createStatement();
			int valide = stmt.executeUpdate("INSERT INTO Reg_Venta \r\n" + 
					"           (Id_Product\r\n" + 
					"           ,cant_Product)\r\n" + 
					"     VALUES\r\n" + 
					"           ('"+ n.getId() + "'\r\n" + 
					"           ," + _cant + ")");		
			if(valide>0) 
			{
				
				
				return "la venta se ha registrado satisfactoriamente";		
			}
			else 
			{
				return "Hubo incoveniente en el registro de venta";
			}
			
		}
		
		catch (Exception e) 
		{
			return "Connection Failed. User/Passwd Error? Message: " + e.getMessage();
					
		}
		
	}

	public String InsertarProducto(String  _Id,String  _producto,String  _des,String  _cant) 
	{
		try 
		{
			Statement stmt = cnt.createStatement();
			int valide = stmt.executeUpdate("INSERT INTO Reg_Product \r\n" + 
					"           (Id_Product\r\n" + 
					"           ,Nom_Product\r\n" + 
					"           ,Desc_Product)\r\n" + 
					"           ,cant_Product)\r\n" +
					"     VALUES\r\n" + 
					"           ('"+ _Id + "'\r\n" + 
					"           ,'" + _producto + "'\r\n" + 
					"           ,'" + _des + "'\r\n" + 
					"           ," + _cant + ")");		
			if(valide>0) 
			{
				return "El producto se ha registrado satisfactoriamente";	
			}
			else 
			{
				return "Hubo incoveniente con el producto";
			}
			
		}
		
		catch (Exception e) 
		{

			return "Connection Failed. User/Passwd Error? Message: " + e.getMessage();			
		}
		
	}


	public boolean ModificarLModulo(String _Id,String  _cant) 
	{
		try 
		{
			Statement stmt = cnt.createStatement();
			int valide = stmt.executeUpdate("UPDATE A SET \r\n" + 
					"           A.cant_Product =  " + _cant +" \r\n" + 
					"           FROM Reg_Product A  \r\n" + 
					"            WHERE  A.Id_Product = '" + _Id +"' ");		
			if(valide>0) 
			{
				return true;	
			}
			else 
			{
				return false;
			}
			
		}
		
		catch (Exception e) 
		{
			System.err.println("Connection Failed. User/Passwd Error? Message: " + e.getMessage());
			return false;			
		}
		
	}
	
}
