

import java.util.ArrayList;
import java.util.List;
/* classe Message
 * Gestion des messages envoyés par le PC et reçus du PC
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
			List<Integer> result = new ArrayList<Integer>();
			int idx = payload.indexOf("=");
			int idx2 = payload.indexOf(",");
			while ((idx != -1) && (idx != payload.length())) {
				if (idx2 == -1) idx2 = payload.length();
				result.add(Integer.parseInt(payload.substring(idx+1, idx2)));
				idx = payload.indexOf("=", idx2+1);
				idx2 = payload.indexOf(",", idx2+1);
			}
			
			return result;
		}
}