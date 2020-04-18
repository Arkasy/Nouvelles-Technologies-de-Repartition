package com.istv.airbnb.Repository;

import com.istv.airbnb.Model.Location;
import com.istv.airbnb.Model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAllByOwnerNot(User user);

    List<Location> findAllByOwner(User user);

    Location findById(int id);

    Object findAll(Specification<Object> and);
}
