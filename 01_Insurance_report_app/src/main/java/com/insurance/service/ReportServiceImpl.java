package com.insurance.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.insurance.entity.CitizenPlan;
import com.insurance.repo.CitizenPlanRepository;
import com.insurance.request.SearchRequest;
import com.insurance.util.EmailUtils;
import com.insurance.util.ExcelGenerator;
import com.insurance.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planrepo;

	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;

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

		List<CitizenPlan> records = planrepo.findAll();

		excelGenerator.generate(response, records);

		String subject = "Test mail subjext";

		String body = "<h1> Test mail body</h1>";

		String to = "shribinwade.9103@gmail.com";

		File f = new File("Plans.xls");

		emailUtils.sendEmail(subject, body, to, f);

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<CitizenPlan> plans = planrepo.findAll();

		File f = new File("Plans.pdf");
		
		pdfGenerator.generate(response, plans,f);

		String subject = "PDF file generated";

		String body = "<h1> Test mail body</h1>";

		String to = "shribinwade.9103@gmail.com";

		

		emailUtils.sendEmail(subject, body, to, f);

		f.delete();
		return true;
	}

}
