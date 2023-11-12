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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;


public class TelaBase extends JFrame implements ActionListener {

    private JTextField textField;
    String selectedFolderPath;



    public TelaBase() {
        setTitle("Gerador de testes para aplicativos mobile");
        setBounds(300, 300, 650, 175);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String caminhoicon = "imagens/Icones.png";
        ImageIcon imagemicon = new ImageIcon(caminhoicon);
        setIconImage(imagemicon.getImage());

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

        JButton SobreButton = new JButton("Sobre...");
        SobreButton.setBounds(545, 100, 75, 25);
        SobreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JanelaCreditos();
            }
        });

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
        TelaTestar.add(SobreButton);

        TelaTestar.add(textField);

        return TelaTestar;
    }

    private void JanelaCreditos() {
        JFrame Janelaso = new JFrame("SOBRE O PROJETO");
        Janelaso.setSize(350, 400);
        Janelaso.setResizable(false);
        Janelaso.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Janelaso.setLayout(null);
        String caminhoiconc = "imagens/Icones.png";
        ImageIcon imagemiconc = new ImageIcon(caminhoiconc);
        Janelaso.setIconImage(imagemiconc.getImage());
        JPanel Janelasobre = new JPanel();
        Janelasobre.setBounds(-30, 10, 400, 400);
        String texto = "<html><body><div style='text-align: center; font-family: Arial; font-size: 10px; color: black;'>Projeto feito por:<br><br>Andrey Gabriel dos Anjos Mendes<br><br>Beatriz Guedes da Silva<br><br>Emanuelle Rocha Marreira<br><br>Italo Ferreira Fonseca<br><br>João Vitor Silva de Carvalho<br><br>Alunos de Engenharia de Computação da UEA<br><br>(Universidade Estadual do Amazonas)<br><br>Professor Orientador:<br><br>Jonathas Silva</div></body></html>";
        JLabel label = new JLabel(texto);
        label.setBounds(50, 50, 390, 390);
        Janelaso.add(Janelasobre);
        Janelasobre.add(label);
        Janelaso.setLocationRelativeTo(null);
        Janelaso.setVisible(true);
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
            JOptionPane.showMessageDialog(this, "Nenhuma permissão encontrada.");
        } else {
            dispose();
            new TelaBase().gerarTextoTeste(permissoes);

        }
    }

    public void removeEmptyRows(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            boolean isEmpty = true;

            for (int j = 0; j < model.getColumnCount(); j++) {
                Object value = model.getValueAt(i, j);
                if (value != null && !value.toString().trim().isEmpty()) {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                model.removeRow(i);
            }
        }
    }
    private void gerarTextoTeste(List<String> permissoes) {
        setVisible(false);
        dispose();
        JFrame Janelaresul = new JFrame("RESULTADO TESTE");
        Janelaresul.setSize(1400, 600);
        Janelaresul.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String caminhoiconr = "imagens/Icones.png";
        ImageIcon imagemiconr = new ImageIcon(caminhoiconr);
        Janelaresul.setIconImage(imagemiconr.getImage());

        String[] coluna = {"<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Pré-requisito</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Teste</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Resultado Esperado</div></body></html>"};
        DefaultTableModel model = new DefaultTableModel(0, coluna.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };

        for (int i = 0; i < coluna.length; i++) {
            model.setColumnIdentifiers(coluna);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(SwingConstants.TOP);

        JTable table = new JTable(model);
        table.setDefaultRenderer(Object.class, renderer);

        // Define a largura preferencial para cada linha
        table.setRowHeight(50); // Defina a altura desejada para cada linha









        String[] teste_internet = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi desligado</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar app com 4G</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Páginas, transições e informações serem carregadas normalmente</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi desligado</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Realizar operações no app com 4G</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Operações funcionarem normalmente</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi ligado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um contexto e desligar o wifi enquanto navega</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Checar modais de erro de carregamento nas tela (''Falha ao carregar dados/página'')</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi ou G ligada</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Alternar entre wifi e 4G enquanto navega pelo app</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Páginas, transições e informações serem carregadas normalmente</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Modo avião habilitado</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar app sem internet</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Checar modais de erro de carregamentos nas telas (''Falha ao carregar dados/página'')</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Modo avião habiltado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Realizar operações no app sem internet</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Checar modais/telas de erro</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi/rede móvel ligado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um contexto e desligar a rede móvel enquanto navega</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Checar modais de erro de carregamentos nas telas (''Falha ao carregar dados/página'')</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi/Rede móvel desligado</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Habilitar intemet e acessar app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Ao clicar no botão de tentar novamente, os dados serem carregados normalmente</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Wifi/Rede móvel desligado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Habilitar internet e realizar operações no app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Operações funcionarem normalmente</div></body></html>"
        };

        String[] teste_camera = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Nenhuma permissão de câmera concedida</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acesse um recurso que utiliza a câmera (por exemplo, tirar uma foto ou escanear um código QR)</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>A câmera deve ser ativada corretamente, e as imagens são processadas conforme esperado</div></body></html>"
        };

        String[] teste_Acessibilidade = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 9px; color: black;'>Geral - Recursos de acessibilidade ligada (ex: controles de interagao, correção de cor, texto em alto contraste, legendas ampliação etc)</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Navegar pelo app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Recursos de acessibilidade funcionarem corretamente junto com funcionalidades do app</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Inversão de cores ligado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Navegar pelo app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Todos os textos e cores podem ser distinguidas</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Correção de cores ligado</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Navegar pelo app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Nenhum texto ou imagem é apagado</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Text to speach</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Navegar pelo app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>A sequência de objetos lidos na tela segue uma sequência lógica e é possível navegar pelo app</div></body></html>"};

        String[] teste_Bloquear_tela = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um módulo</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Apertar a tecla para bloquear a tela e entrar novamente</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Páginas, transições e informações serem carregadas normalmente</div></body></html>"
        };

        String[] teste_armazenamento = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Nenhuma permissão de armazenamento externo concedida</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Realize operações que envolvam leitura e gravação de arquivos no armazenamento externo do dispositivo</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>O aplicativo deve lidar corretamente com permissões de armazenamento, permitindo a leitura e gravação de arquivos externos</div></body></html>"
        };


        String[] teste_Atender_ligacao = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um módulo</div></body></html>" ,"<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Receber uma ligação, deixar tocar e depois voltar para o app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo), - Verificar que a ligação está sobrepondo a aplicação (não está apenas no modal superior)</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um módulo</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Receber uma ligação, atender e depois voltar para o app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo), Quando a ligação é atendida e colocada no ouvido, a aplicação não está sendo mantida na tela e não estamos mexendo nela sem saber</div></body></html>",
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessar um módulo</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Receber uma ligação, recusar chamada e depois voltar para o app</div></body></html>", "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo)</div></body></html>"

        };

        String[] teste_localizacao = {
                "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Nenhuma permissão de localização concedida</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acesse um módulo que utiliza a localização</div></body></html>","<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>A aplicação deve solicitar permissões de localização e obter a localização correta do dispositivo</div></body></html>"
                };

        if (permissoes.contains("INTERNET") || permissoes.contains("ACCESS_NETWORK_STATE") || permissoes.contains("CHANGE_NETWORK_STATE")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Internet</div></body></html>", teste_internet);
        }
        if (permissoes.contains("ACCESS_FINE_LOCATION")||permissoes.contains("ACCESS_COARSE_LOCATION")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Localização</div></body></html>", teste_localizacao);
        }
        if (permissoes.contains("CAMERA")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Camera</div></body></html>", teste_camera);
        }
        adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Acessibilidade</div></body></html>", teste_Acessibilidade);
        if (permissoes.contains("READ_EXTERNAL_STORAGE")||permissoes.contains("WRITE_EXTERNAL_STORAGE")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Armazenamento</div></body></html>", teste_armazenamento);
        }
        if (permissoes.contains("WAKE_LOCK")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Bloquear tela</div></body></html>", teste_Bloquear_tela);
        }
        if (permissoes.contains("READ_PHONE_STATE")) {
            adicionarDadosAoModel(model, "<html><body><div style='text-align: left; font-family: Dialog; font-size: 10px; color: black;'>Atender ligação</div></body></html>", teste_Atender_ligacao);
        }


        removeEmptyRows(model);
        Janelaresul.add(new JScrollPane(table));

        JScrollPane scrollPane = new JScrollPane(table);
        Janelaresul.add(scrollPane);

        Janelaresul.setLocationRelativeTo(null);
        Janelaresul.setVisible(true);
    }

    private void adicionarDadosAoModel(DefaultTableModel model, String titulo, String[] dados) {
        model.addRow(new Object[]{"", "", ""});
        model.addRow(new Object[]{titulo, "", ""});
        for (int k = 0; k < dados.length; k += 3) {
            if (k + 2 < dados.length) {
                model.addRow(new Object[]{dados[k], dados[k + 1], dados[k + 2]});
            }
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

