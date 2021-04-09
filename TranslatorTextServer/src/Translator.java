import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Translator {
	private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
	private static final String CLIENT_SECRET = "PUBLIC_SECRET";
	private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
	private static final int WAITING = 0;
    private static final int SENTMSG = 1;
    private static final int LanguageSelect = 2;
    private static final int ANOTHER = 3;
    private int state = WAITING;
    String processInput(String theInput) throws Exception {
        String theOutput = null;
        String text = null;
        String toLang = null;
        String fromLang = "en";

        if (state == WAITING) {
            theOutput = "Please enter text to translate: ";
            state = SENTMSG;
        } 
        else if (state == SENTMSG) {
            if (!theInput.equals("")) {
            	text = theInput;
                state = LanguageSelect;
                theOutput = "Select a language a. Bahasa Malaysia  b. Arabic  c. Korean";
                } 
            else {
                theOutput = "You're supposed to say input text. Please Try again.";
            }
        } else if (state == LanguageSelect) {
        	
            if (theInput.equalsIgnoreCase("bahasa malaysia")) {
            	theOutput = "Selected: Bahasa Malaysia ";
            	toLang = "bm";
                state = ANOTHER;
                
            }
            else if (theInput.equalsIgnoreCase("arabic")) {
                theOutput = "Selected: Arabic ";
                toLang = "ar";
                state = ANOTHER;
               
            } 
            else if (theInput.equalsIgnoreCase("korean")) {
                theOutput = "Selected: Korean ";
                toLang = "ko";
                state = ANOTHER;
            } 
            else {
                theOutput = "Error occured, check server";
            }
        } 
        
       else if (state == ANOTHER) {
    	   
    	   Translator.translate(fromLang, toLang , text);
    	   
        }
        return theOutput;
    }

    public static void translate(String fromLang, String toLang, String text) throws Exception {
		String jsonPayload = new StringBuilder()
			      .append("{")
			      .append("\"fromLang\":\"")
			      .append(fromLang)
			      .append("\",")
			      .append("\"toLang\":\"")
			      .append(toLang)
			      .append("\",")
			      .append("\"text\":\"")
			      .append(text)
			      .append("\"")
			      .append("}")
			      .toString();

			    URL url = new URL(ENDPOINT);
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			    conn.setDoOutput(true);
			    conn.setRequestMethod("POST");
			    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
			    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
			    conn.setRequestProperty("Content-Type", "application/json");

			    OutputStream os = conn.getOutputStream();
			    os.write(jsonPayload.getBytes());
			    os.flush();
			    os.close();

			    int statusCode = conn.getResponseCode();
			    System.out.println("Status Code: " + statusCode);
			    BufferedReader br = new BufferedReader(new InputStreamReader(
			        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
			      ));
			    String output;
			    while ((output = br.readLine()) != null) {
			        System.out.println(output);    
			    }
			    conn.disconnect();
			  }
			
}

