package com.example.backend.api;

import com.example.backend.businesslogic.TestBusiness;
import com.example.backend.exception.BaseException;
import com.example.backend.model.MRegisterRequest;
import com.example.backend.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestApi {

    private final TestBusiness business;

    public TestApi(TestBusiness business) {
        this.business = business;
    }

    @GetMapping
    public TestResponse test() {
        TestResponse response = new TestResponse();
        response.setName("nn");
        response.setFood("2222");
        return response;
    }

    @GetMapping
    @RequestMapping("/data")
    public ArrayList<TestResponse> test2() {
        ArrayList<TestResponse> arr = new ArrayList<>();
        TestResponse userOne = new TestResponse();
        userOne.setFood("1");
        userOne.setName("@geek");
        arr.add(userOne);
        return arr;
    }

    @GetMapping("/{name}/{food}")
    public TestResponse GetData(@PathVariable("name") String name, @PathVariable("food") String food) {
        TestResponse obj = new TestResponse();
        obj.setFood(food);
        obj.setName(name);

//        HttpHeaders headers = new HttpHeaders();
//        ResponseEntity<TestResponse> entity = new ResponseEntity<>(user,headers,HttpStatus.CREATED);

        return obj;
    }


    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody MRegisterRequest request) throws BaseException {

       String response = business.register(request);
        return ResponseEntity.ok(response);
    }


}
