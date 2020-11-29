package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class StatusCredentials {
    @NotNull
    private long id;
    @NotNull
    private boolean status;

    public long getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
