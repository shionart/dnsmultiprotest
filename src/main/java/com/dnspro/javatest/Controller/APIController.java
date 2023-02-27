package com.dnspro.javatest.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnspro.javatest.DTO.JobResponse;
import com.dnspro.javatest.Services.APIServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class APIController {
    
    private final APIServices apiServices;

    @GetMapping("/joblist")
    public ResponseEntity<List<JobResponse>> getJobList() {
        return ResponseEntity.ok(apiServices.getRequestJobList());
    }
    @GetMapping("/jobdetails/{id}")
    public ResponseEntity<JobResponse> getJobDetails(@PathVariable("id") String id) {
        return ResponseEntity.ok(apiServices.getRequestJobDetail(id));
    }
}
