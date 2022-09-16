package pl.com.repositories.github.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Branch {

    @JsonProperty(value = "branch_name")
    private String name;
    @JsonProperty(value = "last_commit_sha")
    private String sha;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}