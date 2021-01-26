package cz.malanius.escqrs.escqrsdemo.projections;

import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.queries.AddressByRegionQuery;
import cz.malanius.escqrs.escqrsdemo.queries.ContactByTypeQuery;
import cz.malanius.escqrs.escqrsdemo.repository.UserReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserProjection {

    private final UserReadRepository readRepository;

    @Autowired
    public UserProjection(UserReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public Set<ContactDTO> handle(ContactByTypeQuery query) {
        UserContacts contacts = readRepository.getUserContacts().get(query.getUserId());
        return contacts.getContactsByType().get(query.getContactType());
    }

    public Set<AddressDTO> handle(AddressByRegionQuery query) {
        UserAddresses addresses = readRepository.getUserAddresses().get(query.getUserId());
        return addresses.getAddressByRegion().get(query.getState());
    }
}
