package com.example.demo;

import com.example.demo.model.requests.GroupRequest;
import com.example.demo.model.responses.StandardResponse;
import com.example.demo.services.GroupService;
import com.example.demo.services.implementations.GroupServiceImp;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping(value = "/group")
    public Object insert(@RequestBody GroupRequest groupRequest) {
        System.out.println("Request:" + new Gson().toJson(groupRequest));

        GroupService groupService = new GroupServiceImp();
        StandardResponse standardResponse = new StandardResponse();
        try {
            if (groupRequest.getProducts() == null) {
                standardResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
                standardResponse.setMessage("Malformed JSON request");
            } else
                standardResponse = groupService.group(groupRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Response:" + new Gson().toJson(standardResponse.response()));
        return standardResponse.response();
    }

}
