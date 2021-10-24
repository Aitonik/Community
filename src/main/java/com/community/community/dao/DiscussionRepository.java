package com.community.community.dao;

import com.community.community.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query("select d from Discussion d where d.profile in (select f from Profile p join p.friends f where p.id=:id) and d.active=true ")
    List<Discussion> getNewsFriends(@Param("id") Long id);

    @Query("select d from Discussion d where d.profile in (select ff from Profile p join p.friends f join f.friends ff where p.id=:id) and d.active=true ")
    List<Discussion> getNewsFriendsFriends(@Param("id") Long id);

    @Query("select d from Discussion d join d.profile p where p.id=:id and d.active=true ")
    List<Discussion> findByProfileId(@Param("id") Long id);

    @Query("select d from Discussion d where d.active=true and d.deleteTime < :current")
    List<Discussion> findForDelete(@Param("current")LocalDateTime current);
}
