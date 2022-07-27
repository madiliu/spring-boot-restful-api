package com.cathay.assignment.currency.repository;

import com.cathay.assignment.currency.po.CurrencyPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyDepository extends JpaRepository<CurrencyPO, Long> {

  Optional<CurrencyPO> findByCode(String code);
}
