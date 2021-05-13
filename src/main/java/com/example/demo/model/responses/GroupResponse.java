package com.example.demo.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupResponse {
    private Map<String, List<String>> groups = new HashMap<>();
}
