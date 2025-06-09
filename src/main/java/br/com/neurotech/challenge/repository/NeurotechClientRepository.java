package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NeurotechClientRepository extends JpaRepository<NeurotechClient, String> {

    @Query("SELECT c FROM NeurotechClient c " +
            "WHERE c.age BETWEEN :minAge AND :maxAge " +
            "AND c.income BETWEEN :minIncome AND :maxIncome")
    Page<NeurotechClient> findEligibleClients(
            @Param("minAge") int minAge,
            @Param("maxAge") int maxAge,
            @Param("minIncome") Double minIncome,
            @Param("maxIncome") Double maxIncome,
            Pageable pageable
    );
}
