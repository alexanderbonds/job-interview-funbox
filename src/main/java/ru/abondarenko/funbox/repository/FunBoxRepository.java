package ru.abondarenko.funbox.repository;

import ru.abondarenko.funbox.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FunBoxRepository extends JpaRepository<Link, Long>, JpaSpecificationExecutor<Link> {
}
