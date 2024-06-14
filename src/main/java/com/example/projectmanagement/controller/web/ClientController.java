package com.example.projectmanagement.controller.web;

// ClientController.java

import com.example.projectmanagement.domain.Client;
import com.example.projectmanagement.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients/clients";
    }

    @GetMapping("/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/add-client";
    }

    @GetMapping("/edit/{id}")
    public String showEditClientForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            // Handle client not found
            return "error";
        }
        model.addAttribute("client", client);
        return "clients/edit-client";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute("client") Client client) {
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @PutMapping("/edit/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute("client") Client client) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            // Handle client not found
            return "error";
        }
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setContactNumber(client.getContactNumber());
        clientService.saveClient(existingClient);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            // Handle client not found
            return "error";
        }
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

}