package teste;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TelaBase extends JFrame implements ActionListener {
    private JTextField textField;
    String selectedFolderPath;

    public TelaBase() {
        setTitle("Gerador de testes para aplicativos mobile");
        setBounds(300, 300, 600, 150);
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
        TesteButton.setBounds(260, 70, 100, 20);
        TesteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFolderPath == null){
                    JOptionPane.showMessageDialog(null, "Nenhum diretório selecionado.");
                }
            }
        });
        TelaTestar.add(TesteButton);

        String texto = "Selecione o arquivo AndroidManifest.xml do seu aplicativo:";
        JLabel label = new JLabel(texto);
        label.setBounds(20, 5, 500, 20);
        TelaTestar.add(label);

        textField = new JTextField();
        textField.setBounds(20, 30, 470, 20);
        textField.setEditable(false);

        JButton BuscarButton = new JButton("Selecionar");
        BuscarButton.setBounds(500, 30, 100, 20);
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
                    GfgXmlExtractor.pegarmanifest(selectedFolderPath);
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
