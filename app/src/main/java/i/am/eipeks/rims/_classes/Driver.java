package i.am.eipeks.rims._classes;



public class Driver {

    private String driverName, driverPhone;

    public Driver(String driverInfo){
        String info[] = driverInfo.split("_");
        this.driverName = info[0];
        this.driverPhone = info[1];
    }

    public Driver(String driverName, String driverPhone) {
        this.driverName = driverName;
        this.driverPhone = driverPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }
}
