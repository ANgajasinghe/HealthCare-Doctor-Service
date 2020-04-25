package utility;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectionBuilder {

	private final String Path = "C:\\PAF";
	private final String fileName = "Connection.xml";
	private Connection MYSQLcon;

	private ConnectionDTO ReadCfg() throws Exception {
		File dirFile = new File(Path);
		if (!dirFile.isDirectory()) {
			dirFile.mkdir();
		} else {
			File XML = new File(Path + "\\" + fileName);
			if (XML.isFile()) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = (Document) db.parse(XML);
				doc.getDocumentElement().normalize();
				NodeList nodeList = doc.getElementsByTagName("connection");
				for (int itr = 0; itr < nodeList.getLength(); itr++) {
					Node node = nodeList.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;
						ConnectionDTO connectionDTO = new ConnectionDTO();
						connectionDTO.setUrlString(eElement.getElementsByTagName("url").item(0).getTextContent());
						connectionDTO.setUserNameString(eElement.getElementsByTagName("username").item(0).getTextContent());
						connectionDTO.setPasswordString(eElement.getElementsByTagName("password").item(0).getTextContent());
						return connectionDTO;
					}

				}

			}

		}
		ConnectionDTO connectionDTO = new ConnectionDTO();
		return connectionDTO;

	}
	
	public Connection MYSQLConnection()
	{
		try {
			ConnectionDTO cDto = this.ReadCfg();
			Class.forName("com.mysql.jdbc.Driver");
			//MYSQLcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare?useTimezone=true&serverTimezone=UTC", "root", "");
			MYSQLcon = DriverManager.getConnection(cDto.getUrlString(),cDto.getUserNameString(),cDto.getPasswordString());

			return MYSQLcon;
		} catch (Exception e) {
			System.out.println("Akalanka" + e);
			return null;
		}
	}
	
}
