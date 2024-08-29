package com.example.demo.repository;



import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {

    public abstract User findByName(String name);

    public abstract User findByEmail(String email);

    public abstract User findByAge(int age);

    public abstract User findById(long id);

    public abstract List<User> findAll();


}
