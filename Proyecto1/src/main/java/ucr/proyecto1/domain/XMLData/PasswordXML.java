package ucr.proyecto1.domain.XMLData;

import java.util.List;
import org.jdom2.Element;
import ucr.proyecto1.domain.TXTData.PasswordEncryption;
import ucr.proyecto1.domain.data.User;
import util.Utility;

public class PasswordXML {

    private UserXMLData userXMLData;

    public PasswordXML() {
        try {
            userXMLData = new UserXMLData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String email, String password) {
        List<User> users = userXMLData.getAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(PasswordEncryption.encryptPassword(password))) {
                Utility.roleUsuarioActivo = user.getRole();
                Utility.nameUsuarioActivo = user.getName();
                Utility.emailUsuarioActivo = user.getEmail();
                Utility.passwordUsuarioActivo = user.getPassword();
                return true;
            }
        }
        return false;
    }
}
