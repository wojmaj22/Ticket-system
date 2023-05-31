package com.majchrzw.springboot.ticketSystem.dao;

import com.majchrzw.springboot.ticketSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
