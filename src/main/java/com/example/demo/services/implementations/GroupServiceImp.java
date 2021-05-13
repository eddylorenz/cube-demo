package com.example.demo.services.implementations;

import com.example.demo.model.requests.GroupRequest;
import com.example.demo.model.responses.GroupResponse;
import com.example.demo.model.responses.StandardResponse;
import com.example.demo.services.GroupService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImp implements GroupService {

    @Override
    public StandardResponse group(GroupRequest groupRequest) throws Exception{
        GroupResponse groupResponse = new GroupResponse();
        StandardResponse standardResponse = new StandardResponse();
        List<String> validGroups = new ArrayList<String>(){{
            add("fruit");
            add("drink");
        }};
        try {
            groupRequest.getProducts().entrySet().forEach( product -> {
                try {
                    if (product.getKey().isEmpty())
                        throw new Exception("Empty category is an exception during execution time");
                    if  (!(standardResponse.getHttpStatus() == null ))
                        return;
                    if (!validGroups.contains(product.getValue())) {
                        standardResponse.setHttpStatus(HttpStatus.FORBIDDEN);
                        standardResponse.setMessage("Group [" + product.getValue() + "] not allowed" );
                        return;
                    }

                    if (groupResponse.getGroups().containsKey(product.getValue())) {
                        List<String> list = groupResponse.getGroups().get(product.getValue());
                        list.add(product.getKey());
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(product.getKey());
                        groupResponse.getGroups().put(product.getValue(), list);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if  (!(standardResponse.getHttpStatus() == null )) {
                if (groupRequest.getProducts().containsValue(""))
                    throw new Exception(" << FORCED EXCEPTION >> Empty group name is an exception during execution time");
                return standardResponse;
            }
            Map<String, List<String>> sortedMap = new TreeMap<String, List<String>>(groupResponse.getGroups());
            groupResponse.setGroups(sortedMap);
            standardResponse.setHttpStatus(HttpStatus.OK);
            standardResponse.setData(new Gson().toJson(groupResponse.getGroups()));
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
        return standardResponse;
    }
}
