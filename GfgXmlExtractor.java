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

    public static void pegarmanifest(String path){
        try {
            // Criando uma instância do arquivo XML
            File file = new File(path);

            // Criando uma instância do DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Criando um DocumentBuilder para analisar o arquivo XML
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Normalizando o documento XML
            doc.getDocumentElement().normalize();
            // Procurando as tags <uses-permission> no documento
            NodeList nodeList = doc.getElementsByTagName("uses-permission");
            // Criando um StringBuilder para armazenar todas as permissões
            StringBuilder permissaoBuilder = new StringBuilder();
            // Iterando por todos os nós encontrados
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String permissionName = element.getAttribute("android:name");
                    permissaoBuilder.append(permissionName).append("\n");
                }
            }
            // Escrevendo todas as permissões no arquivo de texto
            ManipuladorArquivo.escritor(path, permissaoBuilder.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String argv[]) throws IOException {

    }
    

}

