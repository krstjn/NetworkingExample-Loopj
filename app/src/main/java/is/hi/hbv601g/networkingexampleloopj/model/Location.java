package is.hi.hbv601g.networkingexampleloopj.model;

import org.json.JSONException;
import org.json.JSONObject;

 public class Location {
    private String name;
    private String url;

    public Location(JSONObject json) throws JSONException {
        this.name = json.getString("name");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
