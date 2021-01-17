package com.example.todolist;

public class Todo {
    String task;
    boolean done;

    @Override
    public String toString() {
        return this.task;
    }

    public Todo(String task, boolean done) {
        this.task = task;
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }
}
