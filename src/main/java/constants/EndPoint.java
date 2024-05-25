package constants;

public enum EndPoint {

    DAY1("/basic_auth");

    public final String url;
    EndPoint(String url) {
        this.url = url;
    }
}
