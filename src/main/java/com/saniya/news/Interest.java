package com.saniya.news;

import com.saniya.news.Security.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "interests")
    private Set<User> users;

    public Interest() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
