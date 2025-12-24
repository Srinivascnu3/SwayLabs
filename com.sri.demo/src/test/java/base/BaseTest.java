package base;



import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    protected static ThreadLocal<RequestSpecification> request = new ThreadLocal<>();
    protected static ThreadLocal<StringBuilder> apiLogs = new ThreadLocal<>();

    public void initRequest() {
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";

        request.set(RestAssured.given()
                .header("Content-Type", "application/json"));
        apiLogs.set(new StringBuilder());
    }

    public RequestSpecification getRequest() {
        return request.get();
    }

    public void log(String msg) {
        apiLogs.get().append(msg).append("\n");
    }

    public String getLogs() {
        return apiLogs.get().toString();
    }

    public void clear() {
        request.remove();
        apiLogs.remove();
        
    }
}
