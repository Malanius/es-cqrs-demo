package cz.malanius.escqrs.escqrsdemo.projections;

import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class UserContacts {
    private Map<String, Set<ContactDTO>> contactsByType = new HashMap<>();
}
