package vs.fridgewebserver.http.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by franky3er on 25.04.17.
 */
public class HTTPRequest {
    private HTTPRequestMethod method;
    private Map<String, String> params;
    private String version;
    private String URI;
    private String host;

    HTTPRequest(){
        setParams(new HashMap<String, String>());
    }

    public void parseRequest(String httpRequest){
        //TODO parse Requestmethod
    }

    public HTTPRequestMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPRequestMethod method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
