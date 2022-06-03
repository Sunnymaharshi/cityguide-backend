package com.cityguide.backend;

import com.cityguide.backend.entities.User;
import com.cityguide.backend.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BackendApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	@Order(1)
	void testcreate() {
		User user=new User();
		user.setEmailid("r");
		user.setPassword("r");
		user.setRole("Admin");
		user.setUsername("Random");
		user.setMob_no("1111");
		user.setName("Random");
		userRepository.save(user);
		assertNotNull(userRepository.findById("Random").get());

	}

	@Test
	@Order(2)
	void readall()
	{
		List<User> userList;
		userList=userRepository.findAll();
		assertThat(userList).size().isGreaterThan(0);
	}

	@Test
	@Order(3)
	void testone()
	{
		User user=userRepository.findById("Random").get();
		assertEquals("Admin",user.getRole());
	}

}
