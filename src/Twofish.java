import com.chilkatsoft.CkCrypt2;
import java.util.Random;

public class Twofish {
    private int Encryption; // 128 bits / 192 bits / 256 bits
    private String Key;
    private String PlainText;
    private String CryptedText;

    public Twofish(){
        this.Encryption = 128;
        this.Key = generateKey();
    }

    // METHODES

    // Génerer une clé en hexadécimale
    public String generateKey(){
        String hex = "0123456789ABCDEF";
        String key = "";
        Random random = new Random();

        // Pour le chiffrement 256 bits, la clé secrète binaire est de 32 octets.
        // 24 octets pour le 192 bits
        // 16 octets pour le 128 bits
        int bitEcr = (this.Encryption == 128 ? 16 : this.Encryption == 192 ? 24 : 32);

        for (int i = 0; i < bitEcr * 2; i++) {
            int n =  random.nextInt(16); // 0-15 (index of hex)
            key += hex.charAt(n);
        }

        return key;
    }

    // Crypter un texte
    public void encrypt(){
        CkCrypt2 crypt = new CkCrypt2();

        // Définir l’algorithme de cryptage "twofish"
        crypt.put_CryptAlgorithm("twofish");

        // "ecb" ou "cbc"
        crypt.put_CipherMode("ecb");

        crypt.put_KeyLength(this.Encryption); // 128, 192, 256

        /*
            Le "padding scheme" détermine le contenu des octets ajoutés pour ajouter le résultat
            à un multiple de la taille de bloc de l’algorithme de chiffrement.
            Ex : Si Twofish a une taille de bloc de 16 octets, donc la sortie cryptée est toujours
            un multiple de 16.
         */
        crypt.put_PaddingScheme(0);

        // Codage de la sortie pour le chiffrement et l’entrée pour le déchiffrement ('hex' ou 'base64' ou 'quoted-printable').
        crypt.put_EncodingMode("hex");

        crypt.SetEncodedKey(this.Key,"hex");

        this.CryptedText = crypt.encryptStringENC(this.PlainText);
    }

    // Décrypter un texte
    public void decrypt(){
        CkCrypt2 crypt = new CkCrypt2();
        crypt.put_CryptAlgorithm("twofish");
        crypt.put_CipherMode("ecb");
        crypt.put_KeyLength(this.Encryption);
        crypt.put_PaddingScheme(0);
        crypt.put_EncodingMode("hex");
        crypt.SetEncodedKey(this.Key,"hex");

        this.PlainText = crypt.decryptStringENC(this.CryptedText);
    }

    //

    // GETTERS & SETTERS
    public int getEncryption() {
        return Encryption;
    }

    public void setEncryption(int encryption) {
        Encryption = encryption;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getPlainText() {
        return PlainText;
    }

    public void setPlainText(String plainText) {
        PlainText = plainText;
    }

    public String getCryptedText() {
        return CryptedText;
    }

    public void setCryptedText(String cryptedText) {
        CryptedText = cryptedText;
    }
}
