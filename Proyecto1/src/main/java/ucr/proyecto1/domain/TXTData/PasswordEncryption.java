package ucr.proyecto1.domain.TXTData;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
    public static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //generar un resumen criptográfico (hash) de la contraseña
            //SHA-256 es un algoritmo de hash criptográfico que se usa comúnmente para almacenar contraseñas de forma segura.

            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8)); //convierte la cadena de contraseña a una matriz de bytes

            StringBuilder hexString = new StringBuilder(2 * hash.length); //cada byte del hash se convertirá a dos dígitos hexadecimales, se establece en el doble de la longitud de la matriz

            for (byte b : hash) { // itera sobre cada byte en la matriz
                String hex = Integer.toHexString(0xff & b); //se convierte cada byte del hash a una cadena hexadecimal.

                if (hex.length() == 1) { //verifica si la cadena hexadecimal que representa el byte tiene una longitud de 1
                    hexString.append('0');

                    /*Si la cadena hexadecimal tiene una longitud de 1, se agrega un cero inicial a hexString para garantizar
                    que todos los bytes del hash se representen con dos dígitos.
                     */
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
