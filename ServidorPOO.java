import java.net.*;
import java.io.*;

public class ServidorPOO implements Runnable
{
 Socket socket=null;

 public static void main(String a[])
 {
  ServerSocket serverSocket=null;

  try
  {
   System.out.println("Escuchando por el puerto 8000");
   serverSocket = new ServerSocket(8000); 
  }
  catch(IOException e)
  {
   System.out.println("java.io.IOException generada");
   e.printStackTrace();
  }

  System.out.println("Esperando a que los clientes se conecten...");
  while(true)
  {
   try
   {
    ServidorPOO s = new ServidorPOO();
    s.socket = serverSocket.accept();
    System.out.println("Se conecto un cliente: " + s.socket.getInetAddress().getHostName());
    new Thread( s ).start();
   }
   catch(IOException e)
   {
    System.out.println("java.io.IOException generada");
    e.printStackTrace();
   }
  } // Fin de while
 } // Fin de main

 public void run()
 {
  try
  {
   DataInputStream dis = new DataInputStream( socket.getInputStream() );
   String peticion = dis.readUTF();
   System.out.println("El mensaje que me envio el cliente es: " + peticion);
   String respuesta = buscar(peticion);
   DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
   System.out.println("El mensaje que le envio al cliente es: " + respuesta);
   dos.writeUTF(respuesta);
   dos.close();
   dis.close();
  }
  catch(IOException e)
  {
   System.out.println("java.io.IOException generada");
   e.printStackTrace();
  }
 } // Fin de run

 public synchronized String buscar(String palabra) throws IOException
 {
  BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream("datos.txt") ) );
  String respuesta = "";
  String linea = "";
  while( (linea = br.readLine()) != null )
  {
   if( linea.contains( palabra ) )
    respuesta += linea + '\n';
  }
  br.close();
  return respuesta;
 } // Fin de buscar()
}
