package vs.fridgewebserver.http.request;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPMethodNotImplementedException;
import vs.fridgewebserver.http.exception.HTTPRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public HTTPRequest() {
        params = new HashMap<>();
    }

    public void parseRequest(BufferedReader httpRequest) throws HTTPRequestException {
        String line;
        List<String> lines = new ArrayList<>();
        try {
            while ((line = httpRequest.readLine()) != null && !line.isEmpty()) {
                lines.add(line);
            }
            parseRequest(lines);
        } catch (IOException e) {
            System.err.println("ERROR : Reading Request failed");
            e.printStackTrace();
            System.err.flush();
        }
    }

    public void parseRequest(List<String> httpRequest) throws HTTPRequestException {
        if (httpRequest.isEmpty()) {
            throw new HTTPBadRequestException("Empty Request");
        }
        parseRequestLine(httpRequest.get(0));
    }

    private void parseRequestLine(String requestLine) throws HTTPRequestException {
        String[] requestLineElements = requestLine.split(" ");
        setMethod(requestLineElements[0]);

    }

    public HTTPRequestMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPRequestMethod method) throws HTTPMethodNotImplementedException {
        switch(method){
            case GET: {
                this.method = method;
                break;
            }
            default: {
                throw new HTTPMethodNotImplementedException(method);
            }
        }

    }

    public void setMethod(String methodString) throws HTTPRequestException{
        try {
            HTTPRequestMethod method = HTTPRequestMethod.valueOf(methodString);
            setMethod(method);
        } catch (IllegalArgumentException e) {
            throw new HTTPBadRequestException(String.format("Invalid Method field: %s", methodString));
        }
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
