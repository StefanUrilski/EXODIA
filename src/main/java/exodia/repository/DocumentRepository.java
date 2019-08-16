package exodia.repository;

import exodia.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    @Query(value = "select d from Document as d join d.user as u where u.id = :userId")
    List<Document> findAllByUserId(@Param(value = "userId") String userId);
}
