package cz.malanius.escqrs.escqrsdemo.projections;

import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class UserAddresses {
    private Map<String, Set<AddressDTO>> addressByRegion = new HashMap<>();
}
