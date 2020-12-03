package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 60)
    private String title;

    @NotEmpty
    @NotNull
    @Size(min = 1, max = 1000)
    private String text;

    @NotNull
    @Size(max = 100)
    private String tags;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getTags() {
        return tags;
    }
}
