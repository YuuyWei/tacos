package com.yuuy.tacos.repositories;

import com.yuuy.tacos.entities.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
