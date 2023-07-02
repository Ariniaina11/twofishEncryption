import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel rootPnl;
    private JTabbedPane tabbedPane1;
    private JTextArea plainTxt;
    private JTextField keyEcrTxt;
    private JButton generateBtn;
    private JComboBox encryptionCb;
    private JButton cryptBtn;
    private JTextArea resultEcrTxt;
    private JTextArea cryptedTxt;
    private JTextField keyDcrTxt;
    private JButton decryptBtn;
    private JTextArea resultDcrTxt;

    //

    private Twofish TWOFISH;

    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public Main(){
        init();

        // Clique sur "génerer une clé"
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateKeyAction();
            }
        });

        // Evènement sur le comboBox
        encryptionCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptionCbSelectionChangeAction();
            }
        });

        // Clique sur "CRYPTER"
        cryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cryptAction();
            }
        });

        // Clique sur "DECRYPTER"
        decryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptAction();
            }
        });
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.setContentPane(main.rootPnl);
        main.setTitle("Algorithme de Twofish");
        main.setSize(600, 600);
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Initialisation
    private void init(){
        encryptionCb.addItem("Chiffrement de 128 bits");
        encryptionCb.addItem("Chiffrement de 192 bits");
        encryptionCb.addItem("Chiffrement de 256 bits");

        TWOFISH = new Twofish();
        keyEcrTxt.setText(TWOFISH.getKey());
    }

    // ------------------------- ACTIONS ----------------------------//

    // Une action pour la génération du clé
    private void generateKeyAction() {
        String key = TWOFISH.generateKey();
        TWOFISH.setKey(key);

        keyEcrTxt.setText(TWOFISH.getKey());
    }

    // Une action pour la sélection du comboBox
    private void encryptionCbSelectionChangeAction() {
        int index = encryptionCb.getSelectedIndex();
        int encryption = (index == 0 ? 128 : index == 1 ? 192 : 256);
        TWOFISH.setEncryption(encryption);
        generateKeyAction();
    }

    // Une action pour le cryptage
    private void cryptAction() {
        TWOFISH.setPlainText(plainTxt.getText());
        TWOFISH.encrypt();

        resultEcrTxt.setText(TWOFISH.getCryptedText());
    }

    // Une action pour le décryptage
    private void decryptAction() {
        TWOFISH.setKey(keyDcrTxt.getText());
        TWOFISH.setCryptedText(cryptedTxt.getText());
        TWOFISH.decrypt();

        resultDcrTxt.setText(TWOFISH.getPlainText());
    }
}
