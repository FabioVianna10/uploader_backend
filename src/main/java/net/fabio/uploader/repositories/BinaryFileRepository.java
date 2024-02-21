package net.fabio.uploader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.fabio.uploader.model.*;

@Repository
public interface BinaryFileRepository extends JpaRepository<BinaryFileEntity, Long> {
	BinaryFileEntity findByFileEntityId(Long id);
}
