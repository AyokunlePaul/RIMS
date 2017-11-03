package i.am.eipeks.rims._classes._model_class;



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
