package com.leoduarte.model;

public class Task {

    private Integer id;
    private String title;
    private String description;
    private boolean done;

    private void setId(Integer id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setDone(boolean done) {
        this.done = done;
    }

    public static class Builder {

        private Task task;

        public Builder() {
            task = new Task();
        }

        public Builder id(Integer id) {
            task.setId(id);
            return this;
        }

        public Builder title(String title) {
            task.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            task.setDescription(description);
            return this;
        }

        public Builder done(boolean done) {
            task.setDone(done);
            return this;
        }

        public Task build() {
            return task;
        }
    }
}
