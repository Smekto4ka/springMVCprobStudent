package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.oogis.clientService.ClientService;
import ru.oogis.entity.Client;

@Controller
//@RequestMapping("/client")
public class WebController {

    private final ClientService clientService;

    @Autowired
    public WebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public String getListClient(Model model) {
        model.addAttribute("listClient", clientService.readAll());
        return "listClient";
    }

    @GetMapping("/new")
    public String formNewClient(Model model) {
        model.addAttribute("client", new Client());
        return "newClient";
    }

    @PostMapping("/newClient")
    public String newStudent(@ModelAttribute("client") Client client) {
        clientService.create(client);
        return "redirect:/list";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        clientService.delete(id);
        return "redirect:/list";
    }
}
