package com.istv.airbnb.Repository;

import com.istv.airbnb.Model.LocationRequest;
import org.springframework.data.repository.CrudRepository;

public interface LocationRequestRepository extends CrudRepository<LocationRequest, Long> {
}
