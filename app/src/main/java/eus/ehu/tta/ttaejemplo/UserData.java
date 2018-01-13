package eus.ehu.tta.ttaejemplo;

public class UserData {
    private static UserData instance = null;
    private String userName;
    private String password;
    private String dni;
    private int userID;

    private UserData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static synchronized UserData getInstance() {
        if (instance == null)
        {
            instance = new UserData();
        }
        return instance;
    }
}
