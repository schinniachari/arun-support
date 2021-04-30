package com.example.Sample.repository;

import com.example.Sample.model.User;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository is a Couchbase repository for any operations with DB
 *
 */
@Repository
public interface UserRepository extends ReactiveCouchbaseRepository<User, String> {
}
