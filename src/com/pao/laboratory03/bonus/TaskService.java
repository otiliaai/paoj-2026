package com.pao.laboratory03.bonus;

import java.util.*;

public class TaskService {
    private static TaskService instance;

    private Map<String, Task> tasksById;
    private Map<Priority, List<Task>> tasksByPriority;
    private List<String> auditLog;
    private int counter;

    private TaskService() {
        tasksById = new HashMap<>();
        tasksByPriority = new EnumMap<>(Priority.class);
        auditLog = new ArrayList<>();
        counter = 1;

        for (Priority priority : Priority.values()) {
            tasksByPriority.put(priority, new ArrayList<>());
        }
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    public Task addTask(String title, Priority priority) {
        String id;
        if (counter < 10) {
            id = "T00" + counter;
        } else if (counter < 100) {
            id = "T0" + counter;
        } else {
            id = "T" + counter;
        }
        counter++;

        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Există deja un task cu id-ul '" + id + "'");
        }

        Task task = new Task(id, title, priority);
        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);
        auditLog.add("[ADD] " + id + ": '" + title + "' (" + priority + ")");

        return task;
    }

    public void assignTask(String taskId, String assignee) {
        Task task = getTaskById(taskId);
        task.setAssignee(assignee);
        auditLog.add("[ASSIGN] " + taskId + " → " + assignee);
    }

    public void changeStatus(String taskId, Status newStatus) {
        Task task = getTaskById(taskId);
        Status oldStatus = task.getStatus();

        if (!oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(oldStatus, newStatus);
        }

        task.setStatus(newStatus);
        auditLog.add("[STATUS] " + taskId + ": " + oldStatus + " → " + newStatus);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return new ArrayList<>(tasksByPriority.getOrDefault(priority, new ArrayList<>()));
    }

    public Map<Status, Integer> getStatusSummary() {
        Map<Status, Integer> summary = new EnumMap<>(Status.class);

        for (Status status : Status.values()) {
            summary.put(status, 0);
        }

        for (Task task : tasksById.values()) {
            summary.put(task.getStatus(), summary.get(task.getStatus()) + 1);
        }

        return summary;
    }

    public List<Task> getUnassignedTasks() {
        List<Task> result = new ArrayList<>();

        for (Task task : tasksById.values()) {
            if (task.getAssignee() == null) {
                result.add(task);
            }
        }

        return result;
    }

    public void printAuditLog() {
        for (String entry : auditLog) {
            System.out.println(entry);
        }
    }

    public double getTotalUrgencyScore(int baseDays) {
        double total = 0;

        for (Task task : tasksById.values()) {
            if (task.getStatus() != Status.DONE && task.getStatus() != Status.CANCELLED) {
                total += task.getPriority().calculateScore(baseDays);
            }
        }

        return total;
    }

    public Task getTaskById(String taskId) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task-ul '" + taskId + "' nu a fost găsit");
        }
        return task;
    }
}