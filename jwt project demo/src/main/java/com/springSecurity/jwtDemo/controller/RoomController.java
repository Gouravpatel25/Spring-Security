package com.springSecurity.jwtDemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ROOM_ADD')")
    public String addRoom() {
        return "Room added";
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'GUEST')")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    public String getRoomById(@PathVariable Long id) {
        return "Room fetched for id: " + id;
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PreAuthorize("hasAuthority('ROOM_VIEW_ALL')")
    public String getRooms() {
        return "All rooms";
    }
}

