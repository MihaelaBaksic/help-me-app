package hr.fer.progi.dao;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {



}
