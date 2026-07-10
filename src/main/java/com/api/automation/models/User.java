package com.api.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    // Constructors
    public User() {}

    public User(String name, String job) {
        this.name = name;
        this.job  = job;
    }

    // Getters and Setters
    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }

    public String getJob()           { return job; }
    public void setJob(String job)   { this.job = job; }

    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }

    public String getFirstName()     { return firstName; }
    public String getLastName()      { return lastName; }
    public String getAvatar()        { return avatar; }
    public String getCreatedAt()     { return createdAt; }
    public String getUpdatedAt()     { return updatedAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name
                + "', job='" + job + "', email='" + email + "'}";
    }
}