import java.io.*;
import java.net.*;

public class ClientTranslateApplication {
	
	public static void main(String[] args) throws Exception {
		
		InetAddress address = InetAddress.getLocalHost();
		int port = 5000;
		Socket socket = null;
		PrintStream in = null;
		BufferedReader out = null;

			try {
				socket = new Socket(address, port);
				in = new PrintStream(socket.getOutputStream());
				out = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				} catch (UnknownHostException e) {
	            System.err.println("Don't know about host: localhost");
	        	} catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to: localhost");
	        }
		
			if (socket != null && in != null && out != null) {
	            try {
	                StringBuffer buf = new StringBuffer(50);
	                int c;
	                String fromServer;
	
	                while ((fromServer = out.readLine()) != null) {
	                    System.out.println(fromServer);
	                    if (fromServer.equals("Bye."))
	                        break;
	                    while ((c = System.in.read()) != '\n') {
	                        buf.append((char)c);
	                    }
	                    
	                    in.println(buf.toString());
	                    in.flush();
	                    buf.setLength(0);
	                }
				
				out.close();
	            socket.close();
				
			}catch (UnknownHostException er) {
				System.out.println("Server not found: " + er.getMessage());
			}catch (IOException er) {
				System.out.println("I/O error: " + er.getMessage());
			}
		
		}
	}
}
