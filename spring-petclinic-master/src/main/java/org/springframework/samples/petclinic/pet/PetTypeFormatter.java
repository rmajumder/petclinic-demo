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
package org.springframework.samples.petclinic.pet;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'. Starting from Spring 3.0, Formatters have
 * come as an improvement in comparison to legacy PropertyEditors. See the following links for more details: - The
 * Spring ref doc: https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#format
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @author rishi
 */
@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final String petServiceEndpoint = "localhost:8084";
    private final String getPetTypesUrl = "http://"+petServiceEndpoint+"/pet-management/pets/pettypes";
    
    @Autowired
    public PetTypeFormatter() {
        
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        
        Collection<PetType> findPetTypes = RestCallManager.
        		Get(getPetTypesUrl, new ParameterizedTypeReference<Collection<PetType>>() {});
        for (PetType type : findPetTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

}
