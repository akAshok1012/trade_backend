package com.tm.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.CreditPaymentTrackDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.repo.CreditPaymentTrackRepo;
import com.tm.app.service.CreditPaymentTrackService;

@Service
public class CreditTrackPaymentServiceImpl implements CreditPaymentTrackService {

	@Autowired
	private CreditPaymentTrackRepo creditTrackRepo;

	@Override
	public CreditPaymentTrack updateCreditPaymentTrack(Long id, CreditPaymentTrackDto creditPaymentTrackDto) {
		CreditPaymentTrack creditPayment = creditTrackRepo.findById(id).orElseThrow();
		creditPayment.setDescription(creditPaymentTrackDto.getDescription());
		creditPayment.setDueDate(creditPaymentTrackDto.getDueDate());
		creditPayment = creditTrackRepo.save(creditPayment);
		return creditPayment;
	}

	@Override
	public Page<CreditPaymentTrack> getCreditPaymentTrack(DataFilter dataFilter) {
		return creditTrackRepo.findByCustomer(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField(),dataFilter.getSortByField())));
	}

}
