package com.tm.app.service;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CreditPaymentTrackDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CreditPaymentTrack;

public interface CreditPaymentTrackService {

	CreditPaymentTrack updateCreditPaymentTrack(Long id, CreditPaymentTrackDto creditPaymentTrackDto);

	Page<CreditPaymentTrack> getCreditPaymentTrack(DataFilter dataFilter);

}
