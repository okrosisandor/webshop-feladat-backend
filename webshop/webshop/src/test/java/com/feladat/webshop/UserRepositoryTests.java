package com.feladat.webshop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.feladat.webshop.entity.Role;
import com.feladat.webshop.entity.User;
import com.feladat.webshop.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Test
	public void testCreateUser() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("nam2020");

		User newUser = new User("nam@codejava.net2", "first", "last", password, password);
		User savedUser = repo.save(newUser);

		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testAssignRoleToUser() {
		Integer userId = 1;
		Integer roleId = 3;
		User user = repo.findById(userId).get();
		user.addRole(new Role(roleId));

		User updatedUser = repo.save(user);
		assertThat(updatedUser.getRoles()).hasSize(1);

	}

	@Test
	public void testAssignRoleToUser2() {
		Integer userId = 4;
		User user = repo.findById(userId).get();
		user.addRole(new Role(1));
		user.addRole(new Role(2));

		User updatedUser = repo.save(user);
		assertThat(updatedUser.getRoles()).hasSize(2);

	}

}
