package http.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import model.Epic;
import service.TaskManager;

import java.io.IOException;

public class EpicHandler extends BaseHttpHandler {
    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private void handleGet(HttpExchange httpExchange, String[] path) throws IOException {
        String response;
        switch (path.length) {
            case (2):
                response = gson.toJson(taskManager.getAllEpic());
                sendText(httpExchange, response, 200);
                break;
            case (3):
            case (4):
                if (path[3].equals("subtasks")) {
                    try {
                        int id = Integer.parseInt(path[2]);
                        Epic epic = taskManager.getEpic(id);
                        if (epic != null) {
                            response = gson.toJson(epic);
                            sendText(httpExchange, response, 200);
                        } else {
                            sendBadReguest(httpExchange);
                        }
                    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                        sendBadReguest(httpExchange);
                    }
                }
                break;
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        String bodyRequest = readText(httpExchange);
        if (bodyRequest.isEmpty()) {
            sendBadReguest(httpExchange);
            return;
        }
        try {
            Epic epic = gson.fromJson(bodyRequest, Epic.class);
            if (taskManager.getEpic(epic.getTaskId()) != null) {
                sendBadReguest(httpExchange);
            } else {
                int epicId = taskManager.addNewEpic(epic);
                sendText(httpExchange, Integer.toString(epicId), 201);
            }
        } catch (JsonSyntaxException e) {
            sendBadReguest(httpExchange);
        }
    }

    private void handleDelete(HttpExchange httpExchange, String[] path) throws IOException {
        try {
            int id = Integer.parseInt(path[2]);
            taskManager.deleteEpic(id);
            sendText(httpExchange, "success", 200);
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            sendBadReguest(httpExchange);
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);
    }
}

