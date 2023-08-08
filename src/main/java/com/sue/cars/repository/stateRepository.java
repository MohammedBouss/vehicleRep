package com.sue.cars.repository;

import com.sue.cars.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
@Repository
public interface stateRepository extends JpaRepository<State,Long> {
    Optional<State> findByName(String name);
    @Query("SELECT s.id , s.name FROM State s WHERE s.country.id= :id")
    List<State> findByCountry(@Param(value = "id") Long id);
    @Query("SELECT s.name FROM State s WHERE s.country.name= :name")
    List<String> findStateByCountryName(@Param(value = "name") String name);
}
