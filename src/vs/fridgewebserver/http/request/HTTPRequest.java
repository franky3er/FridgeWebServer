package vs.fridgewebserver.http.request;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPMethodNotImplementedException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.parser.HTTPRequestParser;
import vs.fridgewebserver.http.request.parser.HTTPRequestParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pojo for an HTTPRequest
 */
public class HTTPRequest {
    private List<String> httpRequest;
    private HTTPRequestMethod method;
    private Map<String, String> params;
    private String version;
    private String URI;
    private String host;
    private String userAgent;
    private String connection;

    public HTTPRequest() {
        params = new HashMap<>();
    }

    /**
     * Parses the http request from the given BufferedReader object.
     *
     * @param httpRequest
     * @throws HTTPRequestException
     */
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

    /**
     * Parses the http request from the given String List object.
     *
     * @param httpRequest
     * @throws HTTPRequestException
     */
    public void parseRequest(List<String> httpRequest) throws HTTPRequestException {
        if (httpRequest.isEmpty()) {
            throw new HTTPBadRequestException("Empty Request");
        }
        this.httpRequest = httpRequest;
        parseRequestLine(this.getHttpRequest().get(0));
        HTTPRequestParser requestParser = HTTPRequestParserFactory.build(this.method);
        requestParser.parse(this);
    }

    /**
     * Parses the first Line of the http request and extracts method, uri, and http version.
     *
     * @param requestLine
     * @throws HTTPRequestException
     */
    private void parseRequestLine(String requestLine) throws HTTPRequestException {
        String[] requestLineElements = requestLine.split(" ");
        if (requestLineElements.length != 3) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        setMethod(requestLineElements[0]);
        setURI(requestLineElements[1]);
        setVersion(requestLineElements[2]);
    }

    public HTTPRequestMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPRequestMethod method) throws HTTPMethodNotImplementedException {
        this.method = method;
    }

    public void setMethod(String methodString) throws HTTPRequestException {
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

    public List<String> getHttpRequest() {
        return httpRequest;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
