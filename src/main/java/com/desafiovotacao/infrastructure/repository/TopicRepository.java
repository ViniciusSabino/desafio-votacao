package com.desafiovotacao.infrastructure.repository;

import com.desafiovotacao.domain.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTitle(String title);
}