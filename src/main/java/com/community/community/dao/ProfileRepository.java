package com.community.community.dao;

import com.community.community.entities.Profile;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
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

    @Query("select p from Profile p where UPPER(p.email) like UPPER(:email)")
    List<Profile> findByEmailLike(@Param("email") String email);

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(p, f) from Profile p join p.friends f where p.id=:id")
    List<Pair<Profile, Profile>> findFr(@Param("id") Long id);

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(f, ff) from Profile p join p.friends f join f.friends ff where p.id=:id")
    List<Pair<Profile, Profile>> findFrFr(@Param("id") Long id);

    @Query("select p from Profile p join p.friendRequests fr where fr.id=:id")
    List<Profile> findRequestsFromMe(@Param("id") Long id);
}
