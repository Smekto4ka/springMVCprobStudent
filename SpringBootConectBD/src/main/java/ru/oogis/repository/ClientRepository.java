package ru.oogis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oogis.entity.Client;

public interface ClientRepository extends JpaRepository<Client , Integer>{
}
