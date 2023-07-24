package com.insurance.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.insurance.entity.CitizenPlan;
import com.insurance.repo.CitizenPlanRepository;
import com.insurance.request.SearchRequest;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planrepo;

	@Override
	public List<String> getPlanNames() {

		return planrepo.getPlanNames();

	}

	@Override
	public List<String> getPlanStatus() {
		// TODO Auto-generated method stub
		return planrepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {

		CitizenPlan entity = new CitizenPlan();

		System.out.println(request);

		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}

		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}

		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {
			String startDate = request.getStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localdate = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(localdate);
		}
		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {
			String EndDate = request.getEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localdate = LocalDate.parse(EndDate, formatter);
			entity.setPlanEndDate(localdate);
		}
		return planrepo.findAll(Example.of(entity));

	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("plans-data");
		Row headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Plan Name");
		headerRow.createCell(3).setCellValue("Plan Status");
		headerRow.createCell(4).setCellValue("Plan Start Date");
		headerRow.createCell(5).setCellValue("Plan End Date");
		headerRow.createCell(6).setCellValue("Benefit Amt");

		List<CitizenPlan> records = planrepo.findAll();

		int dataRowIndex = 1;
		for (CitizenPlan plan : records) {
			Row dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());
			if (null != plan.getPlanStartDate()) {
				dataRow.createCell(4).setCellValue(plan.getPlanStartDate() + "");
			} else {
				dataRow.createCell(4).setCellValue("N/A");
			}
			if (null != plan.getPlanEndDate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanEndDate() + "");
			} else {
				dataRow.createCell(5).setCellValue("N/A");
			}
			if (null != plan.getBenefitAmt()) {
				dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
			} else {
				dataRow.createCell(6).setCellValue("N/A");
			}

			dataRowIndex++;
		}

		// FileOutputStream fos = new FileOutputStream( new File("plans.xls"));
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("Citizen Plans", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);
		
		PdfPTable table = new PdfPTable(6);
		
		table.setSpacingBefore(5);
		
		// Create Table Cells for table header
				PdfPCell cell = new PdfPCell();

				// Setting the background color and padding
				cell.setBackgroundColor(CMYKColor.MAGENTA);
				cell.setPadding(5);

		table.addCell("Id");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");

		List<CitizenPlan> plans = planrepo.findAll();

		for (CitizenPlan plan : plans) {

			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(String.valueOf(plan.getCitizenName()));
			table.addCell(String.valueOf(plan.getPlanName()));
			table.addCell(String.valueOf(plan.getPlanStatus()));

			if (null != plan.getPlanStartDate()) {
				table.addCell(String.valueOf(plan.getPlanStartDate()) + "");
			} else {
				table.addCell("N/A");
			}
			if (null != plan.getPlanEndDate()) {
				table.addCell(String.valueOf(plan.getPlanEndDate()) + "");
			} else {
				table.addCell("N/A");
			}
		}
		document.add(table);
		document.close();
		return true;
	}

}
