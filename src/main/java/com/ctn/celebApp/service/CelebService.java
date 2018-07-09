package com.ctn.celebApp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctn.celebApp.celebrequest.CelebCreateRequest;
import com.ctn.celebApp.celebrequest.EmployeSubmitRequest;
import com.ctn.celebApp.celebrequest.QuizRequest;
import com.ctn.celebApp.celebrequest.StatsTournamentRequest;
import com.ctn.celebApp.celebresponse.CelebDetailsResponse;
import com.ctn.celebApp.entity.Employe;
import com.ctn.celebApp.userresponse.EmployeSubmitResponse;

public interface CelebService {

	public CelebDetailsResponse create(CelebCreateRequest celebCreateRequest);

	public EmployeSubmitResponse submit(EmployeSubmitRequest celebCreateRequest)throws Exception;

	public List<Employe> employe();

	public String save(MultipartHttpServletRequest request);

	public String saveMedia(MultipartHttpServletRequest request, String caption, String hreflink);

	public String saveTournament(MultipartHttpServletRequest request, String tournamenttype);

	public String saveSchdeule(MultipartHttpServletRequest request, String content);

	public String dietSave(MultipartHttpServletRequest request, String content);

	public String statsSave(StatsTournamentRequest request);

	public String quizSave(QuizRequest request);

	public String liveMatch(MultipartHttpServletRequest request);

}
