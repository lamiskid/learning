package com.abolade.Learing.service.implementation;

import com.abolade.Learing.emumerations.Status;
import com.abolade.Learing.repository.ServerRepository;
import com.abolade.Learing.model.Server;
import com.abolade.Learing.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server=serverRepository.findByIpAddress(ipAddress);
        InetAddress address=InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP:Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }



    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all server: ");
        return serverRepository.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("fetching server by id: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating  server: {}",server.getName());
        return serverRepository.save(server);

    }

    @Override
    public Boolean delete(long id) {
        log.info("deleting  server by id: {}", id);
        serverRepository.deleteById(id);
        return true;
    }

    private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try(Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        }catch (IOException exception){
            return false;
        }
    }

    private String setServerImageUrl() {
        String [] imageNames={"1.png","2.png","3.png","4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+imageNames[new Random().nextInt(4)]).toUriString();
    }
}
