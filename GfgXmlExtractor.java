package teste;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

public class GfgXmlExtractor {
    public static void main(String argv[]) throws IOException {
    	String path = "C:\\Users\\aluno\\Desktop\\PATA JAVA\\permissoes.txt";
		
        try {
            // Criando uma instância do arquivo XML
            File file = new File("C:\\Users\\aluno\\Desktop\\PATA JAVA\\TESTEMANIFEST.xml");

            // Criando uma instância do DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Criando um DocumentBuilder para analisar o arquivo XML
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Normalizando o documento XML
            doc.getDocumentElement().normalize();
            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());

            // Procurando as tags <uses-permission> no documento
            NodeList nodeList = doc.getElementsByTagName("uses-permission");

            // Iterando por todos os nós encontrados 
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                System.out.println("\nNome do nó: " + node.getNodeName());
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String permissionName = element.getAttribute("android:name");
                    if (permissionName.equals("android.permission.INTERNET")) {
                        System.out.println("Nome da permissão: Internet");
                    }
                    /*System.out.println("Nome da permissão: " + permissionName);
                    ManipuladorArquivo.escritor(path,permissionName);*/
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

}

