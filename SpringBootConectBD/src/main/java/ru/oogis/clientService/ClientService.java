package ru.oogis.clientService;

import ru.oogis.entity.Client;

import java.util.List;

public interface ClientService {
    public void create(Client client);
    public List<Client> readAll();
    public Client read (int id);
    public boolean delete(int id);
}
