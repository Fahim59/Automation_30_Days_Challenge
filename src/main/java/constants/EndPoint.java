package constants;

public enum EndPoint {

    DAY1("/basic_auth"),
    DAY2("/2016/09/how-to-work-with-disable-textbox-or.html"),
    DAY3("/advanced.html"),
    DAY4("/styled/challenges/growing-clickable.html"),
    DAY5("/apps/verify-account/"),
    DAY6("/progressbar"),
    DAY7("/apps/context-menu/"),
    DAY8("/apps/sortable-list/"),
    DAY9("/shadowdom");

    public final String url;
    EndPoint(String url) {
        this.url = url;
    }
}
