package firsov.study.sravnitaxibot.common.model.citymobil;


public class Request {

    private double del_latitude;
    private double del_longitude;
    private double latitude;
    private double longitude;
    private String ver = "4.59.0";
    private String method = "getprice";

    public Request(double del_latitude, double del_longitude, double latitude, double longitude) {
        this.del_latitude = del_latitude;
        this.del_longitude = del_longitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
