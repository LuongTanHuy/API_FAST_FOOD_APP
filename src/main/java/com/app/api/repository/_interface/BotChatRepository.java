package com.app.api.repository._interface;

import com.app.api.model.BotChatModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotChatRepository extends JpaRepository<BotChatModel,Integer> {

}
