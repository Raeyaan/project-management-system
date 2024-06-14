package com.example.projectmanagement.repository;

// ClientRepository.java


import com.example.projectmanagement.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}