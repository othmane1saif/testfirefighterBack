package com.example.test.technique.controller;

import com.example.test.technique.model.Leave;
import com.example.test.technique.model.User;
import com.example.test.technique.service.LeaveService;
import com.example.test.technique.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.Serializable;
import java.time.LocalDate;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/leave")
@AllArgsConstructor
public class LeaveController {
    private LeaveService leaveService;
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity getLeaves(){
        return ResponseEntity.ok().body(leaveService.getAll());
    }

    @PostMapping("/save_leave")
    public void addLeave(@RequestBody LeaveDto leaveDto) {
        Leave leave = Leave.builder().id(leaveDto.getId()).
                dateDepart(leaveDto.getDateDebut()).
                dateFin(leaveDto.getDateFin()).
                user((User) userService.findOnById(leaveDto.getIdUser())).build();
        leaveService.addLeave(leave);
    }
}

@Builder
@Data
class LeaveDto implements Serializable {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long idUser;
}
