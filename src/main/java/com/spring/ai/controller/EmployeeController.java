// package com.spring.ai.controller;


// import java.time.Duration;

// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.spring.ai.dto.ResponseChunk;
// import com.spring.ai.dto.Query.QueryRequest;
// import com.spring.ai.service.EmployeeService;

// import javax.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import reactor.core.publisher.Flux;

// @RequestMapping("/mcp")
// @RestController
// @RequiredArgsConstructor
// public class EmployeeController {

//     private final EmployeeService employeeService;

//     @PostMapping(path = "/querydsl",produces = MediaType.APPLICATION_NDJSON_VALUE)
//     public Flux<ResponseChunk> queryDSLFilter(@Valid @RequestBody QueryRequest queryRequest){
//         return employeeService.filterEmployeeQueryDSL(queryRequest);

//     }

//     @PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
//     public Flux<ResponseChunk> test(){
//         return Flux.just(new ResponseChunk("1", null),new ResponseChunk("2", null),new ResponseChunk("3", null)).delayElements(Duration.ofSeconds(1));
//     }

// }
