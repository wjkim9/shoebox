package com.test.shoebox.repository;

import com.test.shoebox.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
	@Query(value = "SELECT cm.chatmessage_id, DBMS_LOB.SUBSTR(cm.content, 4000, 1) as content, cm.writer_classify, " +
		       "cm.send_datetime, cm.chatroom_id " +
		       "FROM CHATMESSAGE cm " +
		       "JOIN CHATROOM cr ON cm.chatroom_id = cr.chatroom_id " +
		       "WHERE cr.members_id = :membersId " +
		       "ORDER BY cm.send_datetime DESC", 
		       nativeQuery = true)
		List<Object[]> findChatMessagesByMembersId(@Param("membersId") Long membersId);


}
