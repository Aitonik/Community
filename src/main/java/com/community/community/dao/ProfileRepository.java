package com.community.community.dao;

import com.community.community.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where UPPER(p.email)=UPPER(:email)")
    Profile findByEmail(@Param("email") String email);

    @Query("select f.id from Profile p join p.friends f where p.id=:id")
    List<Long> findFriendIdsById(@Param("id") Long id);

    @Query("select f.id from Profile p join p.friends f where p.id in :ids")
    List<Long> findFriendIdsByIds(@Param("ids") List<Long> ids);

    @Query("select f from Profile p join p.friends f where p.id=:id")
    List<Profile> findFriendsById(@Param("id") Long id);

    @Query("select f from Profile p join p.friends f where p.id in :ids")
    List<Profile> findFriendsByIds(@Param("ids") List<Long> ids);

    @Query("select p from Profile p where UPPER(p.email) like UPPER(CONCAT('%',:email,'%')) or UPPER(p.login) like UPPER(CONCAT('%',:login,'%'))")
    List<Profile> findByEmailLikeOrLoginLike(@Param("email") String email, @Param("login") String login);
}
