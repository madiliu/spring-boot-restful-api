package com.cathay.assignment.coin.repository;

import com.cathay.assignment.coin.po.CoinPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinDepository extends JpaRepository<CoinPO, Long> {

    Optional<CoinPO> findByCode(String code);

}
