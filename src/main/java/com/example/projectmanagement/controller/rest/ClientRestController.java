package com.example.projectmanagement.controller.rest;

// ClientRestController.java


import com.example.projectmanagement.domain.Client;
import com.example.projectmanagement.domain.Project;
import com.example.projectmanagement.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {

        Client client = clientService.getClientById(id);
        return client != null
                ? ResponseEntity.ok(client)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        client.setProjects(new ArrayList<Project>());
        Client createdClient = clientService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }
        client.setProjects(existingClient.getProjects());
        client.setId(id);
        Client updatedClient = clientService.saveClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
