package pl.com.repositories.github.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.com.repositories.github.domain.Branch;
import pl.com.repositories.github.domain.Repository;
import pl.com.repositories.github.exception.ResourceNotFoundException;
import pl.com.repositories.github.exception.WrongTreeException;

@RestController
@RequestMapping("/api/v1/repositories")
public class RepositoryRestController {

    public static final String FIELD_FORK = "fork";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_OWNER_NAME = "login";
    public static final String FIELD_COMMIT_SHA = "sha";
    public static final String PROPERTY_FIELD_FALSE = "false";

    @GetMapping("/{user_name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Repository> findRepositoriesByUserName(@PathVariable("user_name") String userName) {
        String userRepositoriesUrl = "https://api.github.com/users/" + userName + "/repos";
        List<Repository> repositories = new ArrayList<>();
        readTreeFromResponse(getResponseFromUrl(userRepositoriesUrl, HttpMethod.GET)).forEach(s -> {
            if (s.findValue(FIELD_FORK).asText().equals(PROPERTY_FIELD_FALSE)) {
                String repositoryName = s.findValue(FIELD_NAME).asText();
                String owner = s.findValue(FIELD_OWNER_NAME).asText();
                Repository repository = new Repository();
                repository.setName(repositoryName);
                repository.setOwner(owner);
                String userBranchesUrl = "https://api.github.com/repos/" + userName + "/" + repositoryName
                        + "/branches";
                List<Branch> branches = new ArrayList<>();
                readTreeFromResponse(getResponseFromUrl(userBranchesUrl, HttpMethod.GET)).forEach(e -> {
                    Branch branch = new Branch();
                    branch.setName(e.findValue(FIELD_NAME).asText());
                    branch.setSha(e.findValue(FIELD_COMMIT_SHA).asText());
                    branches.add(branch);
                });
                repository.setBranches(branches);
                repositories.add(repository);
            }
        });
        return repositories;
    }

    private ResponseEntity<String> getResponseFromUrl(String url, HttpMethod httpMethod) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, httpMethod, new HttpEntity<>(headers), String.class);
        } catch (HttpClientErrorException e) {
            throw new ResourceNotFoundException("Resourse is not found");
        }
    }

    private JsonNode readTreeFromResponse(ResponseEntity<String> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new WrongTreeException("An error occurred while reading tree of JSON");
        }
    }
}