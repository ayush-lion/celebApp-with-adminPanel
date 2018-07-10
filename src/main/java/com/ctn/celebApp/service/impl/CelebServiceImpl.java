package com.ctn.celebApp.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ctn.celebApp.celebrequest.CelebCreateRequest;
import com.ctn.celebApp.celebrequest.EmployeSubmitRequest;
import com.ctn.celebApp.celebrequest.QuizRequest;
import com.ctn.celebApp.celebrequest.StatsTournamentRequest;
import com.ctn.celebApp.celebresponse.CelebDetailsResponse;
import com.ctn.celebApp.dao.CelebRepository;
import com.ctn.celebApp.dao.EmployeRepository;
import com.ctn.celebApp.dao.FitnessRoutineRepository;
import com.ctn.celebApp.dao.LiveMatchRepository;
import com.ctn.celebApp.dao.MediaCaptionRepository;
import com.ctn.celebApp.dao.MyDietRepository;
import com.ctn.celebApp.dao.NewsFeedRepo;
import com.ctn.celebApp.dao.StatsRepository;
import com.ctn.celebApp.dao.TournamentRepository;
import com.ctn.celebApp.entity.CelebDetails;
import com.ctn.celebApp.entity.Employe;
import com.ctn.celebApp.entity.FitnessRoutine;
import com.ctn.celebApp.entity.LiveMatch;
import com.ctn.celebApp.entity.MediaCaption;
import com.ctn.celebApp.entity.MyDiet;
import com.ctn.celebApp.entity.NewsFeed;
import com.ctn.celebApp.entity.Stats;
import com.ctn.celebApp.entity.Tournament;
import com.ctn.celebApp.enums.Status;
import com.ctn.celebApp.service.CelebService;
import com.ctn.celebApp.userresponse.EmployeSubmitResponse;

@Service
public class CelebServiceImpl implements CelebService {

	@Autowired
	CelebRepository celebRepository;

	@Autowired
	EmployeRepository employeRepo;

	@Autowired
	NewsFeedRepo newsFeedRepo;
	
	@Autowired
	MediaCaptionRepository mediaCaptionRepository;
	
	@Autowired
	TournamentRepository tournamentRepo;
	
	@Autowired
	FitnessRoutineRepository fitnessRoutineRepo;
	
	@Autowired
	LiveMatchRepository liveMatchRepo;
	
	@Autowired
	StatsRepository statsRepo;
	
	@Autowired
	MyDietRepository myDietRepo;
	
	@Override
	public CelebDetailsResponse create(CelebCreateRequest celebCreateRequest) {
		CelebDetailsResponse celebDetailsRespons = new CelebDetailsResponse();
		final CelebDetails celebDetails = celebRepository.findByEmail(celebCreateRequest.getEmail());

		if (celebDetails != null) {
			celebDetailsRespons.setStatus(Status.FAILED);
			celebDetailsRespons.setMessage("Celeb already exist");
			return celebDetailsRespons;
		} else {
			final CelebDetails celebDetail = new CelebDetails();
			celebDetail.setCelebname(celebCreateRequest.getCelebname());
			celebDetail.setEmail(celebCreateRequest.getEmail());
			celebDetail.setHeight(celebCreateRequest.getHeight());
			celebDetail.setProfile(celebCreateRequest.getProfile());
			celebDetail.setOccupation(celebCreateRequest.getOccupation());
			celebDetail.setDob(celebCreateRequest.getDob());
			celebDetail.setAge(celebCreateRequest.getAge());
			celebDetail.setWicket(celebCreateRequest.getWicket());
			celebDetail.setRun(celebCreateRequest.getRun());
			celebDetail.setPlayingrole(celebCreateRequest.getPlayingrole());
			celebDetail.setBattingstyle(celebCreateRequest.getBattingstyle());
			celebDetail.setBowlingstyle(celebCreateRequest.getBowlingstyle());
			celebDetail.setMajorteams(celebCreateRequest.getMajorteams());
			celebDetail.setMatchplayed(celebCreateRequest.getMatchplayed());
			celebDetail.setDate(celebCreateRequest.getDate());

			celebRepository.save(celebDetail);

			celebDetailsRespons.setStatus(Status.SUCCESS);
			celebDetailsRespons.setMessage("All Details saved Successfully");
			return celebDetailsRespons;
		}
	}

	@Override
	public EmployeSubmitResponse submit(EmployeSubmitRequest employeSubmitRequest) throws Exception {
		EmployeSubmitResponse employeSubmitResponse=new EmployeSubmitResponse();
		Employe emp = employeRepo.getLastData(employeSubmitRequest.getEmpname());
		if (emp == null) {
			
			Employe employe = new Employe();
			employe.setEmpname(employeSubmitRequest.getEmpname());
			employe.setDayformatted(employeSubmitRequest.getDayformatted());
			employe.setChekin(employeSubmitRequest.getChekin());
			employeRepo.save(employe);
		} else {
			if (emp.getChekout() == null) {
				emp.setChekout(employeSubmitRequest.getChekout());
				SimpleDateFormat format = new SimpleDateFormat("hh.mm.ss");
				Date date1 = format.parse(emp.getChekin());
				Date date2 = format.parse(employeSubmitRequest.getChekout());
				Integer difference = (int) (date2.getTime() - date1.getTime());				
				
				String differenceSeconds =String.valueOf(difference / 1000 % 60);
			    String differenceMinutes =String.valueOf(difference / (60 * 1000) % 60);
			    String differenceHours =String.valueOf(difference / (60 * 60 * 1000) % 24);
				emp.setDuration(differenceHours+":"+differenceMinutes+":"+differenceSeconds);
				
				employeRepo.save(emp);
				
			} else {
				
				Employe employe = new Employe();
				employe.setEmpname(employeSubmitRequest.getEmpname());
				employe.setDayformatted(employeSubmitRequest.getDayformatted());
				employe.setChekin(employeSubmitRequest.getChekin());
				employeRepo.save(employe);
			}
		}
		employeSubmitResponse.setStatus(Status.SUCCESS);
		return employeSubmitResponse;
	}

	@Override
	public List<Employe> employe() {
	return (List<Employe>) employeRepo.findAll();
	}

	@Override
	public String save(MultipartHttpServletRequest request) {
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/newsfeed/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/newsfeed/"+file.getName();
			NewsFeed newsFeed = new NewsFeed();
			newsFeed.setNewsFeedUrl(url);
			newsFeedRepo.save(newsFeed);	
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	public String saveMedia(MultipartHttpServletRequest request, String caption , String hreflink) {
		
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/media_caption/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/media_caption/"+file.getName();
			MediaCaption mediaCaption = new MediaCaption();
			mediaCaption.setCaption(caption);
			mediaCaption.setHreflink(hreflink);
			mediaCaption.setMediaurl(url);
			mediaCaptionRepository.save(mediaCaption);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	public String saveTournament(MultipartHttpServletRequest request, String tournamenttype) {
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/tournament/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/tournament/"+file.getName();
			Tournament tournament = new Tournament();
			tournament.setTournamenttype(tournamenttype);
			tournament.setUrl(url);
			tournamentRepo.save(tournament);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	

	@Override
	public String statsSave(StatsTournamentRequest request) {
	Stats stats= new Stats();
	stats.setMatches(request.getMatch());
	stats.setWickets(request.getWicket());
	stats.setRuns(request.getRuns());
	stats.setNotout(request.getNotout());
	stats.setFivewicket(request.getFivewicket());
	stats.setFourwicket(request.getFivewicket());
	stats.setThreewicket(request.getFourwicket());
	stats.setFiftes(request.getFiftes());
	stats.setHundred(request.getHundred());
	stats.setCatches(request.getCatches());
	stats.setRunout(request.getRunout());
	stats.setMom(request.getMom());
	stats.setBest(request.getBest());
	stats.setBbf(request.getBbf());
	statsRepo.save(stats);	
	return "ok";
	}

	@Override
	public String saveSchdeule(MultipartHttpServletRequest request, String content) {
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/schedule/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/schedule/"+file.getName();
			FitnessRoutine schedule = new FitnessRoutine();
			schedule.setContent(content);
			schedule.setPicurl(url);
			fitnessRoutineRepo.save(schedule);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	public String dietSave(MultipartHttpServletRequest request, String content) {
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/diet/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/diet/"+file.getName();
			MyDiet mDiet = new MyDiet();
			mDiet.setDieturl(url);
			mDiet.setContent(content);
			myDietRepo.save(mDiet);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@Override
	public String liveMatch(MultipartHttpServletRequest request) {
		Iterator<String> itrator = request.getFileNames();
		LiveMatch liveMatch = new LiveMatch();
		boolean isfirst=false;
		while(itrator.hasNext()) {
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/liveMatch/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			String url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/liveMatch/"+file.getName();
			if(!isfirst) {
				liveMatch.setFirstTeam(url);
				isfirst =true;
				}else {
					liveMatch.setSecondTeam(url);
				}
			
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		liveMatchRepo.save(liveMatch);
		return "ok";
	}

	@Override
	public String quizSave(QuizRequest request) {
		
	return "ok";
	}
}

