package com.sbz.contactbook.repositories;

import com.sbz.contactbook.domain.entities.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.firstName = ?1 AND c.lastName = ?2")
    Optional<Contact> findByFullName(String firstName, String LastName);

    Optional<Contact> findByPhone(String phone);
}
