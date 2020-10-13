package ru.itmo.web.lesson4.model;

import ru.itmo.web.lesson4.util.DataUtil;

public class User {
    private final long id;
    private final String handle;
    private final String name;
//    private final User next;
//    private final User prev;

    public User(long id, String handle, String name/*, User prev, User next*/) {
        this.id = id;
        this.handle = handle;
        this.name = name;
//        this.next = DataUtil.getNext(this);
//        this.prev = DataUtil.getPrev(this);
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

//    public User getNext() { return next; }
//
//    public User getPrev() { return prev; }
}
