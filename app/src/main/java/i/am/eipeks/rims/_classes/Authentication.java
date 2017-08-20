package i.am.eipeks.rims._classes;



public class Authentication {

    private String identificationNumber, password;

    public Authentication(String identificationNumber, String password) {
        this.identificationNumber = identificationNumber;
        this.password = password;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getPassword() {
        return password;
    }
}
