package com.example.demo.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandardResponse {
    private HttpStatus httpStatus = null;
    private String data = "";
    private String message = "";

    public Object response () {

        return ResponseEntity
                .status(this.httpStatus)
                .body(message.isEmpty() ? this.data : "{\"message:\" \"" + this.message + "\"}");
    }
}
