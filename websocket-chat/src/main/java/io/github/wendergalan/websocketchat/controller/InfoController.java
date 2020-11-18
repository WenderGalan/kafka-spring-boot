package io.github.wendergalan.websocketchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    private final SimpUserRegistry simpUserRegistry;

    @GetMapping("/total-usuarios")
    public ResponseEntity totalUsuarios() {
        return ResponseEntity.ok(simpUserRegistry.getUserCount());
    }

    @GetMapping("/total-sessoes")
    public ResponseEntity totalSessoes() {
        Set<SimpUser> users = simpUserRegistry.getUsers();
        int sessions = 0;
        if (!users.isEmpty())
            for (SimpUser user : users)
                sessions += user.getSessions().size();
        return ResponseEntity.ok(sessions);
    }
}
