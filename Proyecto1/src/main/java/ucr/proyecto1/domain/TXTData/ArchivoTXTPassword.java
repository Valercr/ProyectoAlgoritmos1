package ucr.proyecto1.domain.TXTData;


import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularLinkedList;
import ucr.proyecto1.domain.list.ListException;

import java.io.*;
import java.util.StringTokenizer;

public class ArchivoTXTPassword {

    private static final String FILE_NAME = "users.txt"; // almacena la ruta del archivo de texto que se usará para guardar los usuarios.
    private CircularLinkedList users = new CircularLinkedList(); //almacenar los usuarios en memoria durante la ejecución del programa.

    public ArchivoTXTPassword() {
        loadUsersFromFile(); //cargar los usuarios existentes del archivo de texto en la lista circular
    }


    public void registerUser(int id, String name, String email, String password) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password); //se utiliza para encriptar la contraseña del usuario antes de guardarla.
        User user = new User(id,name, email,encryptedPassword); //Se crea un nuevo objeto User con los datos proporcionados y la contraseña encriptada.
        users.append(user); //Se agrega el nuevo usuario a la lista circular
        appendUserToFile(user);  //agregar el nuevo usuario al archivo de texto.
    }


    private void loadUsersFromFile() { //leer los usuarios del archivo de texto y agregarlos a la lista circular

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { //abrir el archivo de texto en modo lectura
            String line; //almacenar cada línea del archivo.

            while ((line = reader.readLine()) != null) { //continuará ejecutándose hasta que se llegue al final del archivo
                StringTokenizer tokenizer = new StringTokenizer(line, ","); //dividir la línea leída del archivo en tokens separados por comas

                if (tokenizer.countTokens() == 4) { //asegura que la línea leída del archivo tenga el formato esperado
                    int id = Integer.parseInt(tokenizer.nextToken()); //obtener el siguiente token para el id del usuario
                    String name = tokenizer.nextToken(); //obtener el siguiente token para el username
                    String email = tokenizer.nextToken();//obtener el siguiente token para el email
                    String encryptedPassword = tokenizer.nextToken();//obtener el siguiente token para la contraseña
                    users.append(new User(id,name, email,encryptedPassword)); //agregar el nuevo objeto a la lista.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendUserToFile(User user) { //contiene el nombre de usuario, la contraseña y el rol de un usuario que se va a registrar.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) { //Se asegura de que el archivo se cierre automáticamente
            writer.write(user.getId() + "," + user.getName()+ "," + user.getEmail()+ ","+user.getPassword()); // escribe la información del usuario en el archivo.
            writer.newLine(); //asegura que los datos de cada usuario se escriban en una línea separada.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean authenticateUser(String email, String password) {
        // Encripta la contraseña proporcionada por el usuario usando el método PasswordEncryption.
        String encryptedPassword = PasswordEncryption.encryptPassword(password);

        try {
            // Obtiene el tamaño de la lista circular de usuarios.
            int size = users.size();

            // Inicia la iteración desde el primer nodo de la lista circular.
            users.next();

            // Bucle para iterar a través de todos los nodos de la lista circular.
            for (int i = 0; i < size; i++) {
                // Obtiene el usuario actual en la lista circular.
                User user = users.getCurrent();

                // Compara el nombre de usuario y la contraseña encriptada con las de la lista
                if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPassword)) {
                    // Si el nombre de usuario y la contraseña coinciden, retorna true.
                    return true;
                }
                // Mueve al siguiente usuario en la lista
                users.next();
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

        return false;
    }


}

