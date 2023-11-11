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
    private void gerarTextoTeste(List<String> permissoes) {
        setVisible(false);
        dispose();
        JFrame Janelaresul = new JFrame("RESULTADO TESTE");
        Janelaresul.setSize(1400, 600);
        Janelaresul.setResizable(false);
        Janelaresul.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String caminhoiconr = "imagens/Icones.png";
        ImageIcon imagemiconr = new ImageIcon(caminhoiconr);
        Janelaresul.setIconImage(imagemiconr.getImage());

        String[] coluna = {"Pré-requisito", "Teste", "Resultado Esperado"};
        DefaultTableModel model = new DefaultTableModel(0, coluna.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        for (int i = 0; i < coluna.length; i++) {
            model.setColumnIdentifiers(coluna);
        }


        String[] teste_internet = {
                "Wifi desligado"," Acessar app com 4G"," Páginas, transições e informações serem carregadas normalmente",
                "Wifi desligado","Realizar operações no app com 4G", "Operações funcionarem normalmente",
                "Wifi ligado", "Acessar um contexto e desligar o wifi enquanto navega", "Checar modais de erro de carregamento nas tela\n(''Falha ap carregar dados/página'')",
                "Wifi ou G ligada","Alternar entre wifi e 4G enquanto navega pelo app","Páginas, transições e informações serem carregadas normalmente",
                "Modo avião habilitado","Acessar app sem internet","Checar modais de erro de carregamentos nas telas (''Falha ao carregar dados/página'')",
                "Modo avião habiltado", "Realizar operações no app sem internet", "Checar modais/telas de erro",
                "Wifi/rede móvel ligado", "Acessar um contexto e desligar a rede móvel enquanto navega","Checar modais de erro de carregamentos nas telas (''Falha ao carregar dados/página'')",
                "Wifi/Rede móvel desligado","Habilitar intemet e acessar app", "Ao clicar no botão de tentar novamente, os dados serem carregados normalmente",
                "Wifi/Rede móvel desligado", "Habilitar internet e realizar operações no app", "Operações funcionarem normalmente"
        };

        String[] teste_camera = {
                "Nenhuma permissão de câmera concedida","Acesse um recurso que utiliza a câmera (por exemplo, tirar uma foto ou escanear um código QR)","A câmera deve ser ativada corretamente, e as imagens são processadas conforme esperado"
        };

        String[] teste_Acessibilidade = {
                "Geral - Recursos de acessibilidade ligada (ex: controles de interagao, correção de cor, texto em alto contraste, legendas ampliacao etc)", "Navegar pelo app", "Recursos de acessibilidade funcionarem corretamente junto com funcionalidades do app",
                "Inversão de cores ligado", "Navegar pelo app", "Todos os textos e cores podem ser distinguidas",
                "Correção de cores ligado", "Navegar pelo app", "Nenhum texto ou imagem é apagado",
                "Text to speach", "Navegar pelo app", "A sequência de objetos lidos na tela segue uma sequência lógica e é possível navegar pelo app"};

        String[] teste_Bloquear_tela = {
                "Acessar um módulo", "Apertar a tecla para bloquear a tela e entrar novamente", "Páginas, transições e informações serem carregadas normalmente"
        };

        String[] teste_armazenamento = {
                "Nenhuma permissão de armazenamento externo concedida"," Realize operações que envolvam leitura e gravação de arquivos no armazenamento externo do dispositivo","O aplicativo deve lidar corretamente com permissões de armazenamento, permitindo a leitura e gravação de arquivos externos"
        };


        String[] teste_Atender_ligacao = {
                "Acessar um módulo" ,"Receber uma ligação, deixar tocar e depois voltar para o app", "Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo), - Verificar que a ligação está sobrepondo a aplicação (não está apenas no modal superior)",
                "Acessar um módulo", "Receber uma ligação, atender e depois voltar para o app", "Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo), Quando a ligação é atendida e colocada no ouvido, a aplicação não está sendo mantida na tela e não estamos mexendo nela sem saber",
                "Acessar um módulo", "Receber uma ligação, recusar chamada e depois voltar para o app", "Quando voltamos para o app depois da ligação, continuamos na mesma tela e no mesmo estado que originalmente deixamos a app (seja logado ou não e independentemente da tela/ação que estávamos fazendo)"

        };

        String[] teste_localizacao = {
                "Nenhuma permissão de localização concedida","Acesse um módulo que utiliza a localização","A aplicação deve solicitar permissões de localização e obter a localização correta do dispositivo"
                };

        if (permissoes.contains("INTERNET") || permissoes.contains("ACCESS_NETWORK_STATE") || permissoes.contains("CHANGE_NETWORK_STATE")) {
            adicionarDadosAoModel(model, "Internet", teste_internet);
        }
        if (permissoes.contains("ACCESS_FINE_LOCATION")||permissoes.contains("ACCESS_COARSE_LOCATION")) {
            adicionarDadosAoModel(model, "Localização", teste_localizacao);
        }
        if (permissoes.contains("CAMERA")) {
            adicionarDadosAoModel(model, "Camera", teste_camera);
        }
        adicionarDadosAoModel(model, "Acessibilidade", teste_Acessibilidade);
        if (permissoes.contains("READ_EXTERNAL_STORAGE")||permissoes.contains("WRITE_EXTERNAL_STORAGE")) {
            adicionarDadosAoModel(model, "Armazenamento", teste_armazenamento);
        }
        if (permissoes.contains("WAKE_LOCK")) {
            adicionarDadosAoModel(model, "Bloquear tela", teste_Bloquear_tela);
        }
        if (permissoes.contains("READ_PHONE_STATE")) {
            adicionarDadosAoModel(model, "Atender ligação", teste_Atender_ligacao);
        }



        JTable tabela = new JTable(model);
        Janelaresul.add(new JScrollPane(tabela));

        JScrollPane scrollPane = new JScrollPane(tabela);
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

