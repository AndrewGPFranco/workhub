package com.agpf.workhub.repositories.subdomains;

import com.agpf.workhub.dtos.subdomains.OutputSubdomain;
import com.agpf.workhub.models.subdomains.Subdomain;
import com.agpf.workhub.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubdomainRepository extends JpaRepository<Subdomain, UUID> {

    Optional<Subdomain> findByNameAndUser(String name, User user);

    @Query("""
            select new com.agpf.workhub.dtos.subdomains.OutputSubdomain(
                s.urlPhoto, s.id, s.name
            ) from Subdomain s where s.user.id = :idUser
            """)
    List<OutputSubdomain> subdomainsByUser(@Param("idUser") Long idUser);
}
