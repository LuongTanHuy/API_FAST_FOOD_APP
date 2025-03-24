package com.app.api.repository._interface;

import com.app.api.model.commentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepository extends JpaRepository<commentModel,Integer> {
}
