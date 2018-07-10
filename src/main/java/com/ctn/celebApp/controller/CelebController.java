package com.ctn.celebApp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctn.celebApp.celebrequest.CelebCreateRequest;
import com.ctn.celebApp.celebrequest.EmployeSubmitRequest;
import com.ctn.celebApp.celebrequest.QuizRequest;
import com.ctn.celebApp.celebrequest.StatsTournamentRequest;
import com.ctn.celebApp.celebresponse.CelebDetailsResponse;
import com.ctn.celebApp.entity.Employe;
import com.ctn.celebApp.service.CelebService;
import com.ctn.celebApp.userresponse.EmployeSubmitResponse;

@RestController
@CrossOrigin
@RequestMapping("/celeb")
public class CelebController {
		
	@Autowired
	CelebService celebService;

	/******************************************** Celeb Create**************************************/
	
	@RequestMapping(value = "/create", produces = "application/json", method = RequestMethod.POST)
	public CelebDetailsResponse celebCreation(@RequestBody CelebCreateRequest celebCreateRequest) {
		
	return celebService.create(celebCreateRequest);
	}
	
	/******************************************* EmployeChekin App*******************************/
	
	@RequestMapping(value = "/submit", produces = "application/json", method = RequestMethod.POST)
	public EmployeSubmitResponse celebCreation(@RequestBody EmployeSubmitRequest employeSubmitRequest) throws Exception  {
		
	return celebService.submit(employeSubmitRequest);
	}
	
	/********************************************* GetEmploye *************************************/
	
	@RequestMapping(value = "/get", produces = "application/json", method = RequestMethod.GET)
	public List<Employe> employe(){
			
	return celebService.employe();
	}
	
	/********************************************** PostImage **************************************/
	
	@RequestMapping(value = "/uploadPost", method = RequestMethod.POST)
	public String save(MultipartHttpServletRequest request) throws Exception {
		System.out.println("============================");
	return celebService.save(request);
	}
	
	/*********************************************** MediaCaption ***********************************/
	
	@RequestMapping(value = "/uploadMediaCaption" , method = RequestMethod.POST)
	public String saveMedia(MultipartHttpServletRequest request, String caption , String hreflink) {
		
	return celebService.saveMedia(request,caption,hreflink);
	}
	
	/*********************************************** tournament ****************************************/
	
	@RequestMapping(value = "/uploadTournament" ,  method = RequestMethod.POST)
	public String saveTournament(MultipartHttpServletRequest request,String tournamenttype) {
		
	return celebService.saveTournament(request,tournamenttype);
	}
	
	/*********************************************** TournamentStats ****************************************/
	
	@RequestMapping(value = "/tournamentStats" ,   method = RequestMethod.POST)
	public String statsSave(StatsTournamentRequest request) {
			
	return celebService.statsSave(request);	
	}
	
	/*********************************************** FitnessSchdule ****************************************/
	
	@RequestMapping(value = "/uploadSchedule" ,   method = RequestMethod.POST)
	public String saveSchedule(MultipartHttpServletRequest request, String content) {
		
	return celebService.saveSchdeule(request,content);	
	}
	
    /*********************************************** Diet ****************************************/
	
	@RequestMapping(value = "/Diet" ,   method = RequestMethod.POST)
	public String dietSave(MultipartHttpServletRequest request, String content) {
		
	return celebService.dietSave(request,content);	
	}
	
	/************************************************ Quiz *************************************************/
	
	@RequestMapping(value = "/Quiz" ,   method = RequestMethod.POST)
	public String quizSave(@RequestBody QuizRequest request) {
	return celebService.quizSave(request);	
	}	
	
	@RequestMapping(value = "/LiveMatch" ,   method = RequestMethod.POST)
	public String liveMatch(MultipartHttpServletRequest request) {

	return celebService.liveMatch(request);
	}	
}
