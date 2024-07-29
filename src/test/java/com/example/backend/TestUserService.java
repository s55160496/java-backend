package com.example.backend;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestUserService {

	@Autowired
	private UserService userService;

	@Test
	void testCreate() throws BaseException {
	User user = 	userService.create(TestData.email,TestData.password,TestData.name);

	// check not null
		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());

		// check equal
		Assertions.assertEquals(TestData.name,user.getName());

		boolean isMatchPass = userService.matchPassword(TestData.password, user.getPassword());

		Assertions.assertTrue(isMatchPass);
		Assertions.assertEquals(TestData.name,user.getName());

	}

	@Test
	void testUpdate() {
	}

	@Test
	void testDelete() {
	}

	interface TestData{
		String email ="phongsawae@gg.com";
		String password = "22222";
		String name = "bank pp";
	}

}
