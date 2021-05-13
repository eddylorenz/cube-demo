package com.example.demo.services;

import com.example.demo.model.requests.GroupRequest;
import com.example.demo.model.responses.StandardResponse;

public interface GroupService {
    StandardResponse group(GroupRequest groupRequest) throws Exception;
}
