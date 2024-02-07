package com.sbz.contactbook.controllers;

import com.sbz.contactbook.domain.entities.Contact;
import com.sbz.contactbook.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    private final ContactService contactService;

    public ContactController(final ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping(path = "/contacts")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact contactSaved = contactService.save(contact);
        return new ResponseEntity<>(contactSaved, HttpStatus.CREATED);
    }

    @GetMapping(path = "/contacts")
    public ResponseEntity<List<Contact>> listContacts() {
        List<Contact> contacts = contactService.findAll();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping(path = "/contacts/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") Long id) {
        Optional<Contact> contact = contactService.findOne(id);
        return contact.map(contactValue -> new ResponseEntity<>(contactValue, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/contacts/{id}")
    public ResponseEntity<Contact> fullUpdate(
            @PathVariable("id") Long id,
            @RequestBody Contact contact
    ) {
        if(!contactService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Contact contactUpdated = contactService.save(id, contact);
        return new ResponseEntity<>(contactUpdated, HttpStatus.OK);
    }

    @DeleteMapping(path = "/contacts/{id}")
    public ResponseEntity deleteContact(@PathVariable("id") Long id) {
        contactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
