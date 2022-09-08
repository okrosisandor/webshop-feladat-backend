package com.feladat.webshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.feladat.webshop.entity.Role;
import com.feladat.webshop.repository.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class RoleRepositoryTests {

	@Autowired 
	private RoleRepository roleRepository;
    
    @Test
    public void testCreateRoles() {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");
         
        roleRepository.saveAll(List.of(user, admin));
         
        long count = roleRepository.count();
        assertEquals(2, count);
    }

}
