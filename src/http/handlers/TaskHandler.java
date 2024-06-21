package http.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import exceptions.ManagerSaveException;
import model.Task;
import service.TaskManager;

import java.io.IOException;


public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private void handleGet(HttpExchange httpExchange, String[] path) throws IOException {
        String response;
        if (path.length == 2) {
            response = gson.toJson(taskManager.getAllTask());
            sendText(httpExchange, response, 200);
        } else {
            try {
                int id = Integer.parseInt(path[2]);
                Task task = taskManager.get(id);
                if (task != null) {
                    response = gson.toJson(task);
                    sendText(httpExchange, response, 200);
                } else {
                    sendBadReguest(httpExchange);
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                sendBadReguest(httpExchange);
            }
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        String bodyRequest = readText(httpExchange);
        if (bodyRequest.isEmpty()) {
            sendBadReguest(httpExchange);
            return;
        }
        try {
            Task task = gson.fromJson(bodyRequest, Task.class);
            if (taskManager.get(task.getTaskId()) != null) {
                taskManager.updateTask(task);
                sendText(httpExchange, "success", 201);
            } else {
                int taskId = taskManager.addNewTask(task);
                sendText(httpExchange, Integer.toString(taskId), 201);
            }
        } catch (ManagerSaveException v) {
            sendHasInteractions(httpExchange);
        } catch (JsonSyntaxException e) {
            sendBadReguest(httpExchange);
        }
    }

    private void handleDelete(HttpExchange httpExchange, String[] path) throws IOException {
        try {
            int id = Integer.parseInt(path[2]);
            taskManager.deleteTask(id);
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
