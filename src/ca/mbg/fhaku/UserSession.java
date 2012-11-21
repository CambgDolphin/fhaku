package ca.mbg.fhaku;

public class UserSession {
    private static UserSession instance = new UserSession();

    private UserSession(){}

    public static UserSession getInstance() {
        return instance;
    }

    private String _doURL = "";

    public void setdoURL(String sURL) {
        this._doURL = sURL;
    }

    public String getdoURL() {
        return _doURL;
    }

    private String _vidURL = "";

    public void setvidURL(String sURL) {
        this._vidURL = sURL;
    }

    public String getvidURL() {
        return _vidURL;
    }

}
