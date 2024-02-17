package net.fabio.uploader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.fabio.uploader.model.*;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
 
}
