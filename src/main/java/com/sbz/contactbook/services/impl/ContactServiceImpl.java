package com.sbz.contactbook.services.impl;

import com.sbz.contactbook.domain.entities.Contact;
import com.sbz.contactbook.repositories.ContactRepository;
import com.sbz.contactbook.services.ContactService;
import com.sbz.contactbook.utils.Rol;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact save(Contact contact) {
        try{
            contact.setFirstName(contact.getFirstName().toUpperCase());
            contact.setLastName(contact.getLastName().toUpperCase());
            contact.setAddress(contact.getAddress().toUpperCase());
            if(contact.checkCompanyRol()){
                contact.setRol(Rol.COMPANY);
                contact.setCompanyName(contact.getCompanyName().toUpperCase());
                contact.setCity(contact.getCity().toUpperCase());
            }
            else
                contact.setRol(Rol.PERSON);

            return contactRepository.save(contact);
        } catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Contact save(Long id, Contact contact) {
        contact.setId(id);
        contact.setFirstName(contact.getFirstName().toUpperCase());
        contact.setLastName(contact.getLastName().toUpperCase());
        contact.setAddress(contact.getAddress().toUpperCase());
        // Find contact by full name
        Optional<Contact> contactByName = contactRepository.findByFullName(contact.getFirstName(), contact.getLastName());
        // Find contact by phone
        Optional<Contact> contactByPhone = contactRepository.findByPhone(contact.getPhone());
        if(contactByName.isPresent() && !Objects.equals(contactByName.get().getId(), id))
            throw new IllegalStateException("A user with this name already exists");

        if(contactByPhone.isPresent() && !Objects.equals(contactByPhone.get().getId(), id))
            throw new IllegalStateException("A user with this phone already exists");

        if(contact.checkCompanyRol()){
            contact.setRol(Rol.COMPANY);
            contact.setCompanyName(contact.getCompanyName().toUpperCase());
            contact.setCity(contact.getCity().toUpperCase());
        }

        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> findAll() {
        Iterable<Contact> contactsIterable = contactRepository.findAll();
        List<Contact> result = new ArrayList<>();
        contactsIterable.forEach(result::add);
        return result;
    }

    @Override
    public Optional<Contact> findOne(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return contactRepository.existsById(id);
    }
}
