import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

/* classe Message
 * Construction des messages de dialogue entre le PC et le robot
 */
public class Message {

	private String payload;
	private String type;
	private List<Integer> values;

	public Message(String payload) {
		super();
		this.payload = payload;
		this.type = type();
		this.values = values();
	}
	
	public String toString(){
		String st = "";
		st += "{type: " + type + " | ";
		st+= "values: " +values; 
		st+= "}";
		
		return st;
	}

	/**
	 * Determine le type du message
	 * @return
	 */
	public String type() {
		String type = "unknown";

		switch (payload.charAt(0)) {
		case 'a':
			type = "angle";
			break;
		case 'd':
			type = "distance";
			break;
		}
		return type;
	}

	/**
	 * Construction de la liste de parametres contenu dans le message
	 * @return
	 */
	public List<Integer> values() {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(payload);
		List<Integer> result = new ArrayList<Integer>();
		
		while (matcher.find()) {
			//System.out.println(matcher.group());
			result.add(Integer.parseInt(matcher.group()));
		}
		return result;
	}
	
	/**
	 * Construction de la liste de parametres contenu dans le message
	 * @return
	 */
	public List<Integer> values2() {
		List<Integer> result = new ArrayList<Integer>();
		int idx = payload.indexOf("=");
		int idx2 = payload.indexOf(",");
		while ((idx != -1) && (idx != payload.length())) {
			if (idx2 == -1) idx2 = payload.length();
			result.add(Integer.parseInt(payload.substring(idx+1, idx2)));
			idx = idx2;
			idx2 = payload.indexOf(",", idx2+1);
		}
		
		return result;
	}
}