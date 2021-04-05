package com.encuesta.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.encuesta.exception.ReportFileNotFoundException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Component
public class ReportService {

	@Autowired
	private DataSource dataSource;

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	private File getReportFile(String reportPath) {
		ServletContext context = getRequest().getSession().getServletContext();
		File reportFile = new File(context.getRealPath(reportPath));
		return reportFile;
	}

	public byte[] generateExcelReport(String inputFileName, Map<String, Object> params)
			throws ReportFileNotFoundException {
		byte[] bytes = null;
		Connection conn = null;

		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {

			conn = dataSource.getConnection();

			 InputStream jrxml = new FileInputStream(getReportFile("/WEB-INF/reportes/"+inputFileName+".jrxml"));

			 //params.put("imagen", "/myfloder/images/"+params.get("imagen"));

			 params.put("imagen", getReportFile("/WEB-INF/reportes/images/"+params.get("imagen")).getAbsolutePath());
			 JasperReport jasper = JasperCompileManager.compileReport(jrxml);
	         JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, (Map)params, conn);

	         SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	         //configuration.setRemoveEmptySpaceBetweenRows(true);
	         //configuration.setWhitePageBackground(true);
	         //configuration.setDetectCellType(true);
	         configuration.setOnePagePerSheet(true);
	         configuration.setIgnoreGraphics(false);


	         Exporter exporter = new JRXlsxExporter();
             exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
             exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
             exporter.setConfiguration(configuration);
             exporter.exportReport();

	         bytes = byteArray.toByteArray();


		} catch (JRException | IOException | SQLException e) {
			throw new ReportFileNotFoundException("Reporte no existe.", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
			conn = null;
		}

		return bytes;
	}

	public byte[] generatePDFReport(String inputFileName, Map<String, Object> params)
			throws ReportFileNotFoundException {
		byte[] bytes = null;
		Connection conn = null;

		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {

			conn = dataSource.getConnection();

			 InputStream jrxml = new FileInputStream("/home/eliezer/git/encuesta-backend/src/webapp/WEB-INF/reportes/"+inputFileName+".jrxml");

			 params.put("imagen", "/home/eliezer/git/encuesta-backend/src/webapp/WEB-INF/reportes/images/"+params.get("imagen"));
			 //params.put("imagen", getReportFile("/WEB-INF/reportes/images/"+params.get("imagen")).getAbsolutePath());
			 
			 JasperReport jasper = JasperCompileManager.compileReport(jrxml);
	         JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, (Map)params, conn);
	         bytes = JasperExportManager.exportReportToPdf(jasperPrint);


		} catch (JRException | IOException | SQLException e) {
			System.out.println(e);
			throw new ReportFileNotFoundException("Reporte no existe.", e);
			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
			conn = null;
		}

		return bytes;
	}
}
