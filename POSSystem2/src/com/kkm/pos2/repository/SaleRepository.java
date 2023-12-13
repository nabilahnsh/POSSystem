package com.kkm.pos2.repository;

import com.kkm.pos2.domain.Sale;

public interface SaleRepository {
	
	void save(Sale sale);
	Sale findByNumber(String number);
}
