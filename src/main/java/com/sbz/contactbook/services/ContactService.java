package com.sbz.contactbook.services;

import com.sbz.contactbook.domain.entities.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact save(Contact contact);

    Contact save(Long id, Contact contact);

    List<Contact> findAll();

    Optional<Contact> findOne(Long id);

    void delete(Long id);

    boolean isExists(Long id);
}
