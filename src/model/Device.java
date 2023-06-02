package model;

public class Device {
    private String ipAddress;
    private double bandWith;
    private String name;

    public Device(String ipAddress, double bandWith, String name) {
        this.ipAddress = ipAddress;
        this.bandWith = bandWith;
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getBandWith() {
        return bandWith;
    }

    public void setBandWith(double bandWith) {
        this.bandWith = bandWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}