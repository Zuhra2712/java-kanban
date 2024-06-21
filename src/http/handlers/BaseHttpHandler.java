package http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler implements HttpHandler {
    protected Gson gson = Managers.getGson();
    protected TaskManager taskManager;


    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    protected void sendText(HttpExchange httpExchange, String response, int statusCode) throws IOException {
        httpExchange.sendResponseHeaders(statusCode, 0);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
        httpExchange.close();
    }

    protected void sendBadReguest(HttpExchange httpExchange) throws IOException {
        sendText(httpExchange, "Not Found", 404);
    }

    protected void sendNotAllowed(HttpExchange httpExchange) throws IOException {
        sendText(httpExchange, "Not Found", 405);
    }

    protected void sendHasInteractions(HttpExchange httpExchange) throws IOException {
        sendText(httpExchange, "Not Acceptable", 406);
    }

    protected String readText(HttpExchange httpExchange) throws IOException {
        return new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

    private void handleDelete(HttpExchange httpExchange, String[] path) {
    }

    private void handlePost(HttpExchange httpExchange) {
    }

    private void handleGet(HttpExchange httpExchange, String[] path) {
    }
}
