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
    DAY9("/shadowdom"),
    DAY10("/FileDownload.html"),
    DAY11("/apps/tags-input-box/"),
    DAY13("/blog/tutorial/selenium-tutorial/selenium-cheat-sheet/"),
    DAY15("/meherpavan/form2/index.html?1537702596407"),
    DAY16("/apps/mouse-hover/"),
    DAY17("/search"),
    DAY18("/apps/covered/#"),
    DAY19("/apps/rating/"),
    DAY20("/apps/qr-code-generator/"),
    DAY23("/apps/redirect/"),
    DAY24("/commerce/tablets"),
    ;

    public final String url;
    EndPoint(String url) {
        this.url = url;
    }
}
