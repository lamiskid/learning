package com.abolade.Learing.controller;


import com.abolade.Learing.emumerations.Status;
import com.abolade.Learing.model.Response;
import com.abolade.Learing.model.Server;
import com.abolade.Learing.service.ServerService;
import com.abolade.Learing.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.abolade.Learing.emumerations.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor

public class Resource {
    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServer() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers",serverService.list(30)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {

        Server  server = serverService.ping(ipAddress);
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",server))
                        .message(server.getStatus()== SERVER_UP ? "ping success":"ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers",serverService.create(server)))
                        .message("server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") long id)  {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",serverService.get(id)))
                        .message("server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") long id)  {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("deleted",serverService.delete(id)))
                        .message("server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}" ,produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/documents/images/"+fileName));

    }
}
