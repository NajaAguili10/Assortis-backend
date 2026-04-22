package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProfileNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfileNoteRepository extends JpaRepository<ProfileNote, Long>, JpaSpecificationExecutor<ProfileNote> {
}
