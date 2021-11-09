package com.example.test.technique.service;

import com.example.test.technique.model.Leave;
import com.example.test.technique.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    private LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public Optional<Leave> getLeaveByUserId(Long id) {
        return leaveRepository.findByUser(id);
    }

    public List getAll() {
        return leaveRepository.findAllActiveLeaves();
    }

    @Transactional
    public void addLeave(Leave leave) {
        leaveRepository.save(leave);
    }
}
