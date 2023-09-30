package com.tm.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.CreditPaymentTrackDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.service.CreditPaymentTrackService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreditPaymentTrackController {

	@Autowired
	private CreditPaymentTrackService creditPaymentTrackService;

	@GetMapping("/credit-payment-details")
	public APIResponse<?> getCreditPaymentTrack(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<CreditPaymentTrack> creditPayment = creditPaymentTrackService.getCreditPaymentTrack(dataFilter);
			return Response.getSuccessResponse(creditPayment,APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/credit-payment/{id}")
	public APIResponse<?> updateCreditPaymentTrack(@PathVariable Long id,
			@RequestBody CreditPaymentTrackDto creditPaymentTrackDto) {
		try {
			CreditPaymentTrack creditPayment = creditPaymentTrackService.updateCreditPaymentTrack(id,
					creditPaymentTrackDto);
			return Response.getSuccessResponse(creditPayment, String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE,creditPayment.getPaidAmount()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}