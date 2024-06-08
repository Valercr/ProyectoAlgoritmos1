package ucr.proyecto1.domain.TXTData;


import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularLinkedList;

import java.io.*;
import java.util.StringTokenizer;

public class ArchivoTXTPassword {

    private static final String FILE_NAME = "users.txt"; // almacena la ruta del archivo de texto que se usará para guardar los usuarios.
    private CircularLinkedList users = new CircularLinkedList(); //almacenar los usuarios en memoria durante la ejecución del programa.

    public ArchivoTXTPassword() {
        loadUsersFromFile(); //cargar los usuarios existentes del archivo de texto en la lista circular
    }


    public void registerUser(String username, String password, String role) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password); //se utiliza para encriptar la contraseña del usuario antes de guardarla.
        User user = new User(username, encryptedPassword, role); //Se crea un nuevo objeto User con los datos proporcionados y la contraseña encriptada.
        users.append(user); //Se agrega el nuevo usuario a la lista circular
        appendUserToFile(user);  //agregar el nuevo usuario al archivo de texto.
    }


    private void loadUsersFromFile() { //leer los usuarios del archivo de texto y agregarlos a la lista circular

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { //abrir el archivo de texto en modo lectura
            String line; //almacenar cada línea del archivo.

            while ((line = reader.readLine()) != null) { //continuará ejecutándose hasta que se llegue al final del archivo
                StringTokenizer tokenizer = new StringTokenizer(line, ","); //dividir la línea leída del archivo en tokens separados por comas

                if (tokenizer.countTokens() == 3) { //asegura que la línea leída del archivo tenga el formato esperado
                    String username = tokenizer.nextToken(); //obtener el siguiente token para el nombre de usario
                    String password = tokenizer.nextToken(); //obtener el siguiente token para la contraseña
                    String role = tokenizer.nextToken(); //obtener el siguiente token para el rol
                    users.append(new User(username, password, role)); //agregar el nuevo objeto a la lista.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendUserToFile(User user) { //contiene el nombre de usuario, la contraseña y el rol de un usuario que se va a registrar.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) { //Se asegura de que el archivo se cierre automáticamente
            writer.write(user.getName() + "," + user.getPassword() + "," + user.getRole()); // escribe la información del usuario en el archivo.
            writer.newLine(); //asegura que los datos de cada usuario se escriban en una línea separada.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

