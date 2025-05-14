package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

}
