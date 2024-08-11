package com.example.backend;

import com.example.backend.entity.Address;
import com.example.backend.entity.Social;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.service.AddressService;
import com.example.backend.service.SocialService;
import com.example.backend.service.UserService;
import com.example.backend.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Test
    @Order(1)
    void testCreate() throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(TestCreateData.email, TestCreateData.password, TestCreateData.name,token,new Date());

        // check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        // check equal
        Assertions.assertEquals(TestCreateData.name, user.getName());

        boolean isMatchPass = userService.matchPassword(TestCreateData.password, user.getPassword());

        Assertions.assertTrue(isMatchPass);
        Assertions.assertEquals(TestCreateData.name, user.getName());

    }

    @Test
    @Order(2)
    void testUpdate() throws UserException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        User updateUser = userService.updateName(user.getId(), TestUpDateData.name);

        Assertions.assertNotNull(updateUser);
        Assertions.assertEquals(TestUpDateData.name, updateUser.getName());

    }

    @Test
    @Order(3)
    void testCreateSocial() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        //frist check Socail null

        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.create(user, TestSocialData.facebook, TestSocialData.line, TestSocialData.instagram, TestSocialData.tiktiok);


        //Check not Null
        Assertions.assertNotNull(social);

        //Check Equals
        Assertions.assertEquals(social.getFacebook(), TestSocialData.facebook);
        Assertions.assertEquals(social.getLine(), TestSocialData.line);
        Assertions.assertEquals(social.getInstagram(), TestSocialData.instagram);
        Assertions.assertEquals(social.getTiktok(), TestSocialData.tiktiok);

    }


    @Test
    @Order(3)
    void testCreateAddress() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        List<Address> addresses = user.getAddress();
        Assertions.assertTrue(addresses.isEmpty());

//        Address address = addressService.create(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
//
//        Assertions.assertNotNull(addresses);
//        Assertions.assertEquals(address.getLine1(), AddressTestCreateData.line1);
//        Assertions.assertEquals(address.getLine2(), AddressTestCreateData.line2);
//        Assertions.assertEquals(address.getZipcode(), AddressTestCreateData.zipcode);

        CreteAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        CreteAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);

    }

    private void CreteAddress(User user, String line1, String line2, String zipcode) {
        Address address = addressService.create(user, line1, line2, zipcode);

        Assertions.assertNotNull(address);
        Assertions.assertEquals(address.getLine1(), line1);
        Assertions.assertEquals(address.getLine2(), line2);
        Assertions.assertEquals(address.getZipcode(), zipcode);
    }


    @Test
    @Order(9)
    void testDelete() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        //Check social
        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(TestSocialData.facebook, social.getFacebook());

        //Check Address
        List<Address> address = user.getAddress();
        Assertions.assertFalse(address.isEmpty());
        Assertions.assertEquals(2, address.size());

        userService.deleteById(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);

        Assertions.assertTrue(optDelete.isEmpty());

    }

    interface TestCreateData {
        String email = "phongsawae@gg.com";
        String password = "22222";
        String name = "bank pp";
    }

    interface TestUpDateData {
        String name = "phongsawat Bank";
    }

    interface TestSocialData {
        String facebook = "phongsawat Bank";
        String line = "";
        String instagram = "";
        String tiktiok = "";
    }

    interface AddressTestCreateData {
        String line1 = "2122-251";
        String line2 = "Muang";
        String zipcode = "14000";
    }

    interface AddressTestCreateData2 {
        String line1 = "191-251";
        String line2 = "Muang";
        String zipcode = "23000";
    }

}
