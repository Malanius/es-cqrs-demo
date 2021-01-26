package cz.malanius.escqrs.escqrsdemo.projections;

import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.repository.UserReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProjector {

    private final UserReadRepository readRepository;

    @Autowired
    public UserProjector(UserReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(UserDTO user) {
        projectContacts(user);
        projectAddresses(user);
    }

    private void projectContacts(UserDTO user) {
        UserContacts userContacts = Optional.ofNullable(readRepository.getUserContacts().get(user.getId()))
                .orElse(new UserContacts());
        Map<String, Set<ContactDTO>> contactByType = new HashMap<>();
        if (user.getContacts() != null) {
            user.getContacts().forEach(c -> {
                Set<ContactDTO> contacts = new HashSet<>();
                contacts.add(c);
                contactByType.put(c.getType(), contacts);
            });
        }
        userContacts.setContactsByType(contactByType);
        readRepository.getUserContacts().put(user.getId(), userContacts);
    }

    private void projectAddresses(UserDTO user) {
        UserAddresses userAddresses = Optional.ofNullable(readRepository.getUserAddresses().get(user.getId()))
                .orElse(new UserAddresses());
        Map<String, Set<AddressDTO>> contactsByRegion = new HashMap<>();
        if (user.getAddresses() != null) {
            user.getAddresses().forEach(a -> {
                Set<AddressDTO> contacts = new HashSet<>();
                contacts.add(a);
                contactsByRegion.put(a.getState(), contacts);
            });
        }
        userAddresses.setAddressByRegion(contactsByRegion);
        readRepository.getUserAddresses().put(user.getId(), userAddresses);
    }
}
