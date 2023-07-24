package com.insurance.runner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.insurance.entity.CitizenPlan;
import com.insurance.repo.CitizenPlanRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CitizenPlanRepository repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		repo.deleteAll();
		
		CitizenPlan c1 =new CitizenPlan();
		c1.setCitizenName("john");
		c1.setGender("Male");
		c1.setPlanStatus("Approved");
		c1.setPlanStartDate(LocalDate.now());
		c1.setPlanEndDate(LocalDate.now().plusMonths(6));
		c1.setPlanName("Cash");
		c1.setBenefitAmt(5000.0);
		
		//Cash plan data
		CitizenPlan c2 = new CitizenPlan();
		c2.setCitizenName("Smith");
		c2.setGender("Male");
		c2.setPlanName("Cash");
		c2.setPlanStatus("Denied");
		c2.setDenialReason("Rent income");
		
		//Cash plan data
		CitizenPlan c3 = new CitizenPlan();
		c3.setCitizenName("Cathy");
		c3.setGender("Fe-Male");
		c3.setPlanStatus("Terminated");
		c3.setPlanStartDate(LocalDate.now().minusMonths(4));
		c3.setPlanEndDate(LocalDate.now().plusMonths(6));
		c3.setPlanName("Cash");
		c3.setTerminationDate(LocalDate.now());
		c3.setBenefitAmt(5000.0);
		c3.setTerminationRsn("Employed");
		
	 //Food plan
		CitizenPlan c4 =new CitizenPlan();
		c4.setCitizenName("David");
		c4.setGender("Male");
		c4.setPlanStatus("Approved");
		c4.setPlanStartDate(LocalDate.now());
		c4.setPlanEndDate(LocalDate.now().plusMonths(6));
		c4.setPlanName("Food");
		c4.setBenefitAmt(4000.0);
		
		//Cash plan data
		CitizenPlan c5 = new CitizenPlan();
		c5.setCitizenName("Robert");
		c5.setGender("Male");
		c5.setPlanName("Food");
		c5.setPlanStatus("Denied");
		c5.setDenialReason("Property income");
		
		//Cash plan data
		CitizenPlan c6 = new CitizenPlan();
		c6.setCitizenName("Orlen");
		c6.setGender("Fe-Male");
		c6.setPlanStatus("Terminated");
		c6.setPlanStartDate(LocalDate.now().minusMonths(4));
		c6.setPlanEndDate(LocalDate.now().plusMonths(6));
		c6.setPlanName("Food");
		c6.setTerminationDate(LocalDate.now());
		c6.setBenefitAmt(5000.0);
		c6.setTerminationRsn("Employed");
		
		//Medical
		CitizenPlan c7 =new CitizenPlan();
		c7.setCitizenName("Charles");
		c7.setGender("Male");
		c7.setPlanStatus("Approved");
		c7.setPlanStartDate(LocalDate.now());
		c7.setPlanEndDate(LocalDate.now().plusMonths(6));
		c7.setPlanName("Medical");
		c7.setBenefitAmt(8000.0);
		
		//Cash plan data
		CitizenPlan c8 = new CitizenPlan();
		c8.setCitizenName("Smith");
		c8.setGender("Male");
		c8.setPlanName("Medical");
		c8.setPlanStatus("Denied");
		c8.setDenialReason("Rent income");
		
		//Cash plan data
		CitizenPlan c9 = new CitizenPlan();
		c9.setCitizenName("Nell");
		c9.setGender("Fe-Male");
		c9.setPlanStatus("Terminated");
		c9.setPlanStartDate(LocalDate.now().minusMonths(4));
		c9.setPlanEndDate(LocalDate.now().plusMonths(6));
		c9.setPlanName("Medical");
		c9.setTerminationDate(LocalDate.now());
		c9.setBenefitAmt(5000.0);
		c9.setTerminationRsn("Govt Employed");
		
		List <CitizenPlan> list = Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8,c9);
		
		repo.saveAll(list);
	}
	

}