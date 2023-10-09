package teste;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TelaBase extends JFrame implements ActionListener {
    private JTextField textField;

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
        TesteButton.addActionListener(e -> buscarAndroidManifest());
        TelaTestar.add(TesteButton);

        String texto = "Selecione a pasta do aplicativo:";
        JLabel label = new JLabel(texto);
        label.setBounds(20, 5, 500, 20);
        TelaTestar.add(label);

        textField = new JTextField();
        textField.setBounds(20, 30, 470, 20);
        textField.setEditable(false);

        JButton BuscarButton = new JButton("Buscar");
        BuscarButton.setBounds(500, 30, 70, 20);
        BuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setDialogTitle("Selecionar Pasta");
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = folderChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // O usuário selecionou um diretório
                    String selectedFolderPath = folderChooser.getSelectedFile().getAbsolutePath();
                    textField.setText(selectedFolderPath);
                } else {
                    System.out.println("Nenhum diretório selecionado.");
                }
            }
        });
        TelaTestar.add(BuscarButton);

        TelaTestar.add(textField);

        return TelaTestar;
    }
    //buscando o arquivo manifest
    private void buscarAndroidManifest() {
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
    }

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
