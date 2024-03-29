/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data.
 * See: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer> {

    /**
     * Retrieve all <code>Vet</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Vet</code>s
     */
    @Transactional(readOnly = true)
    //@Cacheable("vets")
    Collection<Vet> findAll() throws DataAccessException;
    
    /**
     * Retrieve all {@link Specialities}s from the data store.
     * @return a Collection of {@link Specialities}s.
     */
    @Query("SELECT spcl FROM Specialty spcl ORDER BY spcl.name")
    @Transactional(readOnly = true)
    List<Specialty> findVetSpecialities();
    
    /**
     * Retrieve a {@link Vet} from the data store by id.
     * @param id the id to search for
     * @return the {@link Vet} if found
     */
    @Transactional(readOnly = true)
    Vet findById(Integer id);
    
    /**
     * Save a {@link Vet} to the data store, either inserting or updating it.
     * @param vet the {@link Vet} to save
     */
    //@CachePut("vets")
    void save(Vet vet);
}
