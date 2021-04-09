import java.io.*;
import java.net.*;

public class ServerTranslationApplication {

	
    public static void main(String args[]) throws Exception {
    	
    	int port = 5000;
    	String outputLine = null;
    	String inputLine = null;
		ServerSocket SerSocket;
		
    	try{
    		SerSocket = new ServerSocket(port);
    		System.out.println("Server is listening on port " + port);
    	
    	 while(true) {
    		
            
            
            Socket socket = SerSocket.accept();
            
            
    	 	BufferedReader in = new BufferedReader(
 				new InputStreamReader(socket.getInputStream()));
    	 	
    	 	PrintStream out = new PrintStream(
    	             new BufferedOutputStream(socket.getOutputStream(), 1024), false);
    	 	
    	 	Translator kks = new Translator();
    		
    	 	outputLine = kks.processInput(null);
    	 	out.println(outputLine);
    	 	out.flush();
    	 	
    	 	while ((inputLine = in.readLine()) != null) {
    	 	    outputLine = kks.processInput(inputLine);
    	 	    out.println(outputLine);
    	 	    out.flush();
    	 	    if (outputLine.equals("Bye."))
    	 	        break;
    	 	}
    	 	
    	 	in.close();
    	 	out.close();
	    	SerSocket.close();
		    socket.close();
		    }
    	 
    	 }catch (IOException e) {
    		    System.out.println("Could not listen on port: " + port  + ", " + e);
    		    System.exit(1);
    	}
    	
    }
}
   
	
    	
    


