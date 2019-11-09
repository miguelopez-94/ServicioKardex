package src;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Session Bean implementation class Business
 */
@Stateless
@LocalBean
@WebService
public class Business {

    /**
     * Default constructor. 
     */
    public Business() {
        // TODO Auto-generated constructor stub
    }
    
    public ArrayList<Item> lista_Productos() throws Exception 
    {
    	ArrayList<Item> Lista = new ArrayList<Item>();
    	DAO logica = new DAO();
    	//logica.connection();
    	//Lista = logica.obtenerProductos();
    	Item eje = new Item();
    	eje.setId(1);
    	eje.setValue("Camiseta");
    	Lista.add(eje);
    	return Lista;
    }
    
    public String Registro_Producto(String  Id,String  producto,String des,String  cant) 
    {
    	String mensaje = "";
    	DAO logica = new DAO();
    	logica.connection();
    	mensaje = logica.InsertarProducto(Id, producto, des, cant);
    	return mensaje;
    	
    }
    
    public String Registro_Venta(String  Id,String  cant) 
    {
    	String mensaje = "";
    	DAO logica = new DAO();
    	logica.connection();
    	mensaje = logica.InsertarVenta(Id, cant);
    	return mensaje;
    	
    }
    

}
