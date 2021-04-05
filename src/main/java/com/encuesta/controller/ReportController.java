package com.encuesta.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encuesta.exception.ErrorResponse;
import com.encuesta.services.ReportService;

@RestController
@RequestMapping("/reportes/{report_name}")
public class ReportController {

	@Autowired
	private ReportService reportService;


	@PostMapping
	@RequestMapping("/xlsx")
	public ResponseEntity<?> generateExcel(@PathVariable(required = true, name = "report_name") String reportName,
			@RequestBody Map<String, Object> json) {

		try {

			return ResponseEntity.ok()
					.header("Content-Type", "application/vnd.ms-excel")
					.header("Content-Disposition", "inline; filename=\"" + reportName + ".xlsx\"")
					.body(reportService.generateExcelReport(reportName, getParams(json)));


		} catch (Exception e) {
			return new ResponseEntity<>(ErrorResponse.of(e.getMessage(), HttpStatus.NOT_FOUND),
					HttpStatus.NOT_FOUND);
		}
	}


	@PostMapping
	public ResponseEntity<?> generatePdf(@PathVariable(required = true, name = "report_name") String reportName,
			@RequestBody Map<String, Object> json) {

		try {

			return ResponseEntity.ok()
					.header("Content-Type", "application/pdf; charset=UTF-8")
					.header("Content-Disposition", "inline; filename=\"" + reportName + ".pdf\"")
					.body(reportService.generatePDFReport(reportName, getParams(json)));


		} catch (Exception e) {
			return new ResponseEntity<>(ErrorResponse.of(e.getMessage(), HttpStatus.NOT_FOUND),
					HttpStatus.NOT_FOUND);
		}

	}

	private Map<String, Object> getParams(Map<String, Object> originalParams) {

		Map<String, Object> params = new HashMap<>();

		for (Map.Entry<String, Object> entry : originalParams.entrySet()) {
			if(entry.getKey().equalsIgnoreCase("fecha_inicio")
			  || entry.getKey().equalsIgnoreCase("fecha_fin")) {
				params.put(entry.getKey(), Timestamp.valueOf((String)entry.getValue()));
			}else {
				params.put(entry.getKey(), entry.getValue());
			}
		}

		return params;
	}

}
