import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import java.util.Scanner; 


class ClienteREST{
	public static void Alta(Usuario usuario) throws Exception{
		URL url = new URL("http://20.231.26.62:8080/Servicio/rest/ws/alta_usuario");

		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST"); //utilizamos el metodo POST de HTTP

		// indica que la peticion estara codificada como URL
		conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		Gson gson = new GsonBuilder().registerTypeAdapter(byte[].class,new AdaptadorGsonBase64()).create();
		String parametros = "usuario=" + URLEncoder.encode(gson.toJson(usuario), "UTF-8");

		OutputStream os = conexion.getOutputStream();
		os.write(parametros.getBytes());
		os.flush();

		if(conexion.getResponseCode() == 200){	//no hubo error
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));

			String respuesta;
			while ((respuesta = br.readLine()) != null ) 
				System.out.println(respuesta);
			System.out.println("OK");
		}else{	//hubo error			
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));

			String respuesta;
			System.out.println("ERROR: ");
			while((respuesta = br.readLine()) != null) System.out.println(respuesta);
		}
		conexion.disconnect();
	}

	public static void Consulta(String email) throws Exception{
		Scanner sc = new Scanner(System.in);
		URL url = new URL("http://20.231.26.62:8080/Servicio/rest/ws/consulta_usuario");

		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		String parametros = "email=" + URLEncoder.encode( "" + email, "UTF-8");
		OutputStream os = conexion.getOutputStream();

		os.write(parametros.getBytes());
		os.flush();

		if(conexion.getResponseCode() == 200){	//No hubo error
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));

			Usuario userAConsultar = new Usuario();
			char opc2 = ' ';
			String respuesta;

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                
			while((respuesta = br.readLine()) != null){
				userAConsultar = (Usuario) gson.fromJson(respuesta, Usuario.class);
				System.out.println("\n\tDATOS USUARIO:");
				System.out.println("E-mail: " + userAConsultar.email);
				System.out.println("Nombre: " + userAConsultar.nombre);
				System.out.println("Apellido paterno: " + userAConsultar.apellido_paterno);
				System.out.println("Apellido materno: " + userAConsultar.apellido_materno);
				System.out.println("Fecha de nacimiento: " + userAConsultar.fecha_nacimiento);
				System.out.println("Telefono: " + userAConsultar.telefono);
				System.out.println("Genero: " + userAConsultar.genero);
			} 
			
			System.out.println("\tDesea modificar los datos del usuario? (s/n): ");
			opc2 = sc.next().charAt(0);
			sc.nextLine();
			if(opc2 == 's'){
				System.out.println("Modificando datos... ");
				System.out.println("Si deseas que el campo permanezca igual solo presiona enter");

				Usuario usuario = new Usuario(); 
				usuario.email = email;

			  	System.out.print("Nombre: ");
				usuario.nombre = sc.nextLine();
				if(usuario.nombre.equals("")) usuario.nombre = userAConsultar.nombre;

				System.out.print("Apellido paterno: ");
				usuario.apellido_paterno = sc.nextLine();
				if(usuario.apellido_paterno.equals("")) usuario.apellido_paterno = userAConsultar.apellido_paterno;

				System.out.print("Apellido materno: ");
				usuario.apellido_materno = sc.nextLine();
				if(usuario.apellido_materno.equals("")) usuario.apellido_materno = userAConsultar.apellido_materno;

				System.out.print("Fecha de nacimiento: ");
				usuario.fecha_nacimiento = sc.nextLine();
				if(usuario.fecha_nacimiento.equals("")) usuario.fecha_nacimiento = userAConsultar.fecha_nacimiento;				

				System.out.print("Numero de telefono: ");
				usuario.telefono = sc.nextLine();
				if(usuario.telefono.equals("")) usuario.telefono = userAConsultar.telefono;	

				System.out.print("Genero (M/F): ");
				usuario.genero = sc.nextLine();
				if(usuario.genero.equals("")) usuario.genero = userAConsultar.genero;	

				Modificar(usuario);				
			}
		}else{	//Hubo error
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
			String respuesta;

			System.out.println("ERROR: ");
			while((respuesta = br.readLine()) != null) System.out.println(respuesta);
			
		}
		conexion.disconnect();

	}

	public static void Modificar(Usuario usuario) throws Exception{
		URL url = new URL("http://20.231.26.62:8080/Servicio/rest/ws/modifica_usuario");
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		Gson gson = new GsonBuilder().registerTypeAdapter(byte[].class,new AdaptadorGsonBase64()).create();
		String parametros = "usuario=" + URLEncoder.encode(gson.toJson(usuario), "UTF-8");

		OutputStream os = conexion.getOutputStream();
		os.write(parametros.getBytes());
		os.flush();

		if(conexion.getResponseCode() == 200){	//NO hubo error
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));

			String respuesta;			
			while ((respuesta = br.readLine()) != null ) 
				System.out.println(respuesta);
			System.out.println("OK");
		}else{
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
			String respuesta;

			System.out.println("ERROR: ");
			while((respuesta = br.readLine()) != null) 
				System.out.println(respuesta);
		}
		conexion.disconnect();
	}

	public static void Borrar(String email) throws Exception{
		URL url = new URL("http://20.231.26.62:8080/Servicio/rest/ws/borra_usuario");

		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		String parametros = "email=" + URLEncoder.encode("" + email, "UTF-8");

		OutputStream os = conexion.getOutputStream();
		os.write(parametros.getBytes());
		os.flush();

		if(conexion.getResponseCode() == 200){
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));

			String respuesta;			
			while ((respuesta = br.readLine()) != null ) 
				System.out.println(respuesta);
			System.out.println("OK");
		}else{
			BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
			String respuesta;

			System.out.println("ERROR: ");
			while((respuesta = br.readLine()) != null) 
				System.out.println(respuesta);
		}
		conexion.disconnect();		
	}

	public static void main(String [] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		Usuario usuario;
		String email;
		char opc = ' ';
		while(opc != 'd'){
			System.out.println("\na. Alta usuario");
			System.out.println("b. Consulta usuario");
			System.out.println("c. Borra usuario");
			System.out.println("d. Salir");
			System.out.print("\t Opcion: ");
			opc = sc.next().charAt(0);
			sc.nextLine();

			switch(opc){ 
				case 'a':
					System.out.print("Alta usuario: ");
					usuario = new Usuario(); 

					System.out.print("e-mail: ");
				  	usuario.email = sc.nextLine();
				  	System.out.print("Nombre: ");
					usuario.nombre = sc.nextLine();
					System.out.print("Apellido paterno: ");
					usuario.apellido_paterno = sc.nextLine();
					System.out.print("Apellido materno: ");
					usuario.apellido_materno = sc.nextLine();
					System.out.print("Fecha de nacimiento: ");
					usuario.fecha_nacimiento = sc.nextLine();
					System.out.print("Numero de telefono: ");
					usuario.telefono = sc.nextLine();
					System.out.print("Genero: ");
					usuario.genero = sc.nextLine();
				
					Alta(usuario);
					System.out.println("OK");
					break;

				case 'b':
					System.out.print("e-mail del usuario: ");
					email = sc.next();

					System.out.println("\tConsulta usuario:");
					Consulta(email);
					break;

				case 'c':
					System.out.print("e-mail del usuario: ");
					email = sc.next();					
					Borrar(email);
					break;
			}

		}
	}

}

class Usuario {
    int id_usuario;
    String email;
    String nombre;
    String apellido_paterno;
    String apellido_materno;
    String fecha_nacimiento;
    String telefono;
    String genero;
    byte[] foto;

    public Usuario(){}
}