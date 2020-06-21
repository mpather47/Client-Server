/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 *
 * @author 
 */
public class ServerApp
{
    
    // Server socket
    private ServerSocket listener;
    
    // Client connection
    private Socket client;
    
    /** Creates a new instance of ServerApp */
    public ServerApp()
    {
        // Create server socket
        try {
            listener = new ServerSocket(12345, 10);
        }
        catch (IOException ioe)
        {
          System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
    
    public void listen()
    {
        // Start listening for client connections
        try {
          System.out.println("Server is listening");
          client = listener.accept();  
          System.out.println("Now moving onto processClient");
          
          processClient();
        }
        catch(IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
    
    public void processClient()
    {
        // Communicate with the client
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        // First step: initiate channels
        try {
             out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
             in = new ObjectInputStream(client.getInputStream());
             
            // Step 2: communicate
            String msg = (String)in.readObject();
            System.out.println("From CLIENT>> " + msg);
            out.writeObject("Hello " + msg);
            out.flush();
            
            // Step 3:close down
                    
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        } finally {
        try {
            out.close();
            in.close();
            client.close();
            client = listener.accept();  
        } catch(Exception e){}
    }
    }
  
    public static void main(String[] args)
    {
        // Create application
        ServerApp server = new ServerApp();
        
        // Start waiting for connections
        server.listen();
    }    
}