package pl.com.repositories.github.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    @JsonProperty(value = "repository_name")
    private String name;
    @JsonProperty(value = "owner_login")
    private String owner;
    @JsonProperty(value = "branches")
    private List<Branch> branches;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
   
    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}