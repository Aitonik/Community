package com.community.community.dao;

import com.community.community.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    @Query("select i from Invite i where UPPER(i.email)=UPPER(:email) and UPPER(i.code)=UPPER(:code)")
    List<Invite> findAllByEmailAndCode(@Param("email") String email, @Param("code") String code);
}
