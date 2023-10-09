package teste;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
public class ManipuladorArquivo {
	public static void escritor(String path, String permissao) throws IOException {
		String nomedotxt = "permissoes.txt";
		String txt = path.concat(nomedotxt);
		Path pathtxt = Path.of(txt);
		if(Files.notExists(pathtxt)){
			Files.createFile(pathtxt);
		}
		Files.writeString(pathtxt, permissao);
	}
}
