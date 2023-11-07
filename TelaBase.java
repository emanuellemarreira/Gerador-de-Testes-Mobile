package teste;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaBase extends JFrame implements ActionListener {

    private JTextField textField;
    String selectedFolderPath;



    public TelaBase() {
        setTitle("Gerador de testes para aplicativos mobile");
        setBounds(300, 300, 650, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel telaBasePanel = criarTelaBasePanel();
        add(telaBasePanel);

        setVisible(true);
    }

    private JPanel criarTelaBasePanel() {
        JPanel TelaTestar = new JPanel();
        TelaTestar.setLayout(null);

        JButton TesteButton = new JButton("Gerar testes");
        TesteButton.setBounds(270, 70, 100, 25);
        TesteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFolderPath == null){
                    JOptionPane.showMessageDialog(null, "Nenhum diretório selecionado.");
                } else {
                    extrairPermissoesDoManifest(selectedFolderPath);
                }
            }
        });
        TelaTestar.add(TesteButton);


        String texto = "Selecione o arquivo AndroidManifest.xml do seu aplicativo:";
        JLabel label = new JLabel(texto);
        label.setBounds(20, 5, 500, 20);
        TelaTestar.add(label);

        textField = new JTextField();
        textField.setBounds(20, 30, 490, 20);
        textField.setEditable(false);

        JButton BuscarButton = new JButton("Selecionar");
        BuscarButton.setBounds(520, 29, 100, 22);
        BuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Selecionar AndroidManifest.xml");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos XML", "xml");
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // O usuário selecionou um arquivo
                    selectedFolderPath = fileChooser.getSelectedFile().getAbsolutePath();
                    //GfgXmlExtractor.pegarmanifest(selectedFolderPath);
                    textField.setText(selectedFolderPath);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum diretório selecionado.");
                }
            }
        });
        TelaTestar.add(BuscarButton);

        TelaTestar.add(textField);

        return TelaTestar;
    }
    //buscando o arquivo manifest
   /* private void buscarAndroidManifest() {
        String selectedFolderPath = textField.getText();
        if (!selectedFolderPath.isEmpty()) {
            File folder = new File(selectedFolderPath);
            File[] files = folder.listFiles();

            if (files != null) {
                boolean manifestFound = false;
                for (File file : files) {
                    if (file.isFile() && file.getName().equalsIgnoreCase("AndroidManifest.xml")) {
                        manifestFound = true;
                        System.out.println("Arquivo AndroidManifest.xml encontrado em: " + file.getAbsolutePath());
                        GfgXmlExtractor.pegarmanifest(file.getAbsolutePath());
                        break;
                    }
                }
                if (!manifestFound) {
                    JOptionPane.showMessageDialog(this, "Nenhum arquivo AndroidManifest.xml encontrado na pasta selecionada.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "A pasta selecionada está vazia.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma pasta antes de buscar o arquivo AndroidManifest.xml.");
        }
    }*/

    private void extrairPermissoesDoManifest(String caminhoManifest) {
        List<String> permissoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoManifest))) {
            String linha;
            // loop que lê cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                if (linha.contains("android.permission.")) {
                    int startIndex = linha.indexOf("android.permission.") + "android.permission.".length();
                    int endIndex = linha.indexOf("\"", startIndex);
                    if (endIndex > startIndex) {
                        String permissao = linha.substring(startIndex, endIndex);
                        permissoes.add(permissao);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (permissoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma permissão encontrada no arquivo AndroidManifest.xml.");
        } else {
            JOptionPane.showMessageDialog(this, "Permissões encontradas: " + String.join(", ", permissoes));
            //gerarTextoTeste(permissoes);
            /////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////A PARTIR DAQUI//////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////
            //SwingUtilities.invokeLater(() -> new telaDeTeste());
        }
    }

    /*private void gerarTextoTeste(List<String> permissoes) {
        for (String permissao : permissoes) {
            if (permissao.equals("INTERNET")) {
                JOptionPane.showMessageDialog(this, "Permissões encontradas: " + String.join(", ", permissoes) +
                        "\nPré-requisito: Wifi desligado\n" +
                        "Teste: Acessar app com 4G\n" +
                        "Resultados esperados: Páginas, transições e informações serem carregadas normalmente");
            }
        }
    }*/

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new TelaBase());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

