package com.thread.cdr.repository;

import com.thread.cdr.model.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory, Integer> {
    @Query("SELECT ROUND(SUM(c.duration * c.price),2) FROM CallHistory c where c.account = :account")
    Double totalCost(@Param("account") String account);
}
