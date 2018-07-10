package com.ctn.celebApp.service.impl;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctn.celebApp.dao.CelebRepository;
import com.ctn.celebApp.dao.FitnessRoutineRepository;
import com.ctn.celebApp.dao.FixuresRepository;
import com.ctn.celebApp.dao.LikeStatusAllFeedRepository;
import com.ctn.celebApp.dao.LiveMatchRepository;
import com.ctn.celebApp.dao.MediaCaptionRepository;
import com.ctn.celebApp.dao.MyDietRepository;
import com.ctn.celebApp.dao.NewsFeedLikeRepository;
import com.ctn.celebApp.dao.NewsFeedRepo;
import com.ctn.celebApp.dao.PostUrlRepo;
import com.ctn.celebApp.dao.PostVideoRepo;
import com.ctn.celebApp.dao.ProfilePicRepository;
import com.ctn.celebApp.dao.QuizAnswerRepository;
import com.ctn.celebApp.dao.QuizQuestionRepository;
import com.ctn.celebApp.dao.StatsRepository;
import com.ctn.celebApp.dao.SubscribeRepository;
import com.ctn.celebApp.dao.TournamentRepository;
import com.ctn.celebApp.dao.UserCommentRepository;
import com.ctn.celebApp.dao.UserRepository;
import com.ctn.celebApp.dao.VideoUrlRepository;
import com.ctn.celebApp.entity.FitnessRoutine;
import com.ctn.celebApp.entity.Fixures;
import com.ctn.celebApp.entity.LiveMatch;
import com.ctn.celebApp.entity.MediaCaption;
import com.ctn.celebApp.entity.MyDiet;
import com.ctn.celebApp.entity.NewsFeed;
import com.ctn.celebApp.entity.NewsFeedLikeDetails;
import com.ctn.celebApp.entity.ProfilePic;
import com.ctn.celebApp.entity.QuizAnswer;
import com.ctn.celebApp.entity.QuizQuestion;
import com.ctn.celebApp.entity.Stats;
import com.ctn.celebApp.entity.Subscribe;
import com.ctn.celebApp.entity.Tournament;
import com.ctn.celebApp.entity.User;
import com.ctn.celebApp.entity.UserCommentDetails;
import com.ctn.celebApp.entity.VideoUrl;
import com.ctn.celebApp.enums.Status;
import com.ctn.celebApp.service.GenerateRandomPassword;
import com.ctn.celebApp.service.MailSender;
import com.ctn.celebApp.service.UserService;
import com.ctn.celebApp.userrequest.EmailRequest;
import com.ctn.celebApp.userrequest.LikeRequest;
import com.ctn.celebApp.userrequest.LoginWithFacebookRequest;
import com.ctn.celebApp.userrequest.LoginWithGoogle;
import com.ctn.celebApp.userrequest.QuizGameRequest;
import com.ctn.celebApp.userrequest.UserCommentRequest;
import com.ctn.celebApp.userrequest.UserCreateRequest;
import com.ctn.celebApp.userrequest.UserLoginRequest;
import com.ctn.celebApp.userresponse.LikeResponse;
import com.ctn.celebApp.userresponse.NewsFeedResponse;
import com.ctn.celebApp.userresponse.StatusResponse;
import com.ctn.celebApp.userresponse.UserCommentResponse;
import com.ctn.celebApp.userresponse.UserDetailsResponse;
import com.ctn.celebApp.userresponse.UserQuizResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	CelebRepository celebRepo;

	@Autowired
	ProfilePicRepository profileRepo;

	@Autowired
	PostUrlRepo postUrlRepo;

	@Autowired
	GenerateRandomPassword genratedRandomPassword;

	@Autowired
	MailSender mailSender;

	@Autowired
	PostVideoRepo postVideoRepo;
	
	@Autowired
	NewsFeedLikeRepository newsFeedLikeRepo;

	@Autowired
	UserCommentRepository userCommentRepo;
	
	@Autowired
	MediaCaptionRepository mediaCaptionRepo;
	
	@Autowired
	SubscribeRepository subscribeRepo;
	
	@Autowired
	LiveMatchRepository livematchRepo;

	@Autowired
	FitnessRoutineRepository fitnessRoutineRepo;
	
	@Autowired
	NewsFeedRepo newsFeedRepo;
	
	@Autowired
	TournamentRepository tournamentRepo;
	
	@Autowired
	StatsRepository statsRepo;
	
	@Autowired
	FixuresRepository fixureRepo;
	
	@Autowired
	MyDietRepository myDietRepo;
	
	@Autowired
	VideoUrlRepository videourlRepo;
	
	@Autowired
	LikeStatusAllFeedRepository likeStatusAllFeedRepo;
	
	@Autowired
	QuizQuestionRepository quizQuestionRepo;
	
	@Autowired
	QuizAnswerRepository quizAnswerRepo;
	
	@Autowired
	ProfilePicRepository profilePicRepo;
	
	@Override
	public UserDetailsResponse create(UserCreateRequest userCreateRequest) {
		UserDetailsResponse response = new UserDetailsResponse();
		final User user = userRepo.findByEmail(userCreateRequest.getEmail());

		if (user != null) {
			response.setStatus(Status.FAILED);
			response.setMessage("Email is not valid or Email Exist Allready!!");
			return response;
		} else {
			final User newUser = new User();
			newUser.setUserName(userCreateRequest.getUserName());
			newUser.setEmail(userCreateRequest.getEmail());
			newUser.setPassword(userCreateRequest.getPassword());

			userRepo.save(newUser);

			response.setUserName(userCreateRequest.getUserName());
			response.setEmail(userCreateRequest.getEmail());
			response.setStatus(Status.SUCCESS);
			response.setMessage("Details Saved Sucessfully!!");
			return response;
		}
	}

	@Override
	public UserDetailsResponse login(UserLoginRequest userLoginRequest) {

		String oldPassword = userLoginRequest.getPassword();
		final User user = userRepo.findByEmail(userLoginRequest.getEmail());

		UserDetailsResponse userLoginResponse = new UserDetailsResponse();

		if (user == null) {
			userLoginResponse.setStatus(Status.FAILED);
			userLoginResponse.setMessage("Email or password doesnt exist.");
			return userLoginResponse;
		}

		if (!user.getPassword().equals(oldPassword)) {
			userLoginResponse.setStatus(Status.FAILED);
			userLoginResponse.setMessage("Email or password doesnt exist.");
			return userLoginResponse;
		}

		userLoginResponse.setUserId(user.getUserId());
		userLoginResponse.setUserName(user.getUserName());
		userLoginResponse.setEmail(user.getEmail());
		userLoginResponse.setPassword(user.getPassword());
		userLoginResponse.setStatus(Status.SUCCESS);
		userLoginResponse.setMessage("Logged In Successfully");
		return userLoginResponse;
	}

	@Override
	public UserDetailsResponse loginWithFacebook(LoginWithFacebookRequest loginWithFacebookRequest) {

		User u = userRepo.findByEmail(loginWithFacebookRequest.getEmail());
		if (u == null) {
			User newUser = new User();

			newUser.setEmail(loginWithFacebookRequest.getEmail());
			newUser.setUserName(loginWithFacebookRequest.getUsername());
			newUser.setSocialMedia(true);

			userRepo.save(newUser);

			UserDetailsResponse uLR = new UserDetailsResponse();
			uLR.setUserId(userRepo.save(newUser).getUserId());
			uLR.setEmail(loginWithFacebookRequest.getEmail());
			uLR.setUserName(loginWithFacebookRequest.getUsername());
			uLR.setStatus(Status.SUCCESS);
			uLR.setMessage("Details Saved Successfully!!");
			return uLR;
		}
		UserDetailsResponse sR = new UserDetailsResponse();
		sR.setUserId(u.getUserId());
		sR.setUserName(u.getUserName());
		sR.setEmail(u.getEmail());
		sR.setStatus(Status.SUCCESS);
		sR.setMessage("Details Saved Allready");
		return sR;
	}

	@Override
	public UserDetailsResponse loginWithGoogle(LoginWithGoogle loginWithGoogle) {

		User u = userRepo.findByEmail(loginWithGoogle.getEmail());
		if (u == null) {
			User newUser = new User();

			newUser.setEmail(loginWithGoogle.getEmail());
			newUser.setUserName(loginWithGoogle.getUserName());
			newUser.setSocialMedia(true);

			userRepo.save(newUser);

			UserDetailsResponse uLR = new UserDetailsResponse();
			uLR.setUserId(userRepo.save(newUser).getUserId());
			uLR.setEmail(loginWithGoogle.getEmail());
			uLR.setUserName(loginWithGoogle.getUserName());
			uLR.setStatus(Status.SUCCESS);
			uLR.setMessage("Details Saved Successfully!!");
			return uLR;
		}
		UserDetailsResponse sR = new UserDetailsResponse();
		sR.setUserId(u.getUserId());
		sR.setUserName(u.getUserName());
		sR.setEmail(u.getEmail());
		sR.setStatus(Status.SUCCESS);
		sR.setMessage("Details Saved Allready");
		return sR;
	}

	@Override
	public StatusResponse forgotPassword(EmailRequest emailRequest) {
		User u = userRepo.findByEmail(emailRequest.getEmail());
		if (u == null) {
			StatusResponse sR = new StatusResponse();
			sR.setStatus(Status.FAILED);
			sR.setMessage("Email Doesn't Exist");
			return sR;
		}
		String newPass = genratedRandomPassword.RandomPassword();
		String from = "aayush4lion@gmail.com";
		String to = "aayush4lion@gmail.com";
		String subject = "Hi ! Welcome to celeb App";
		String body = "New Password: " + newPass;
		mailSender.sendMail(from, to, subject, body);
		u.setPassword(body);
		userRepo.save(u);
		StatusResponse sR = new StatusResponse();
		sR.setStatus(Status.SUCCESS);
		sR.setMessage("New Password Send to Your EmailId!");
		return sR;
	}

	@Override
	public List<MyDiet> myDiet() {
	return (List<MyDiet>) myDietRepo.findAll();
	}
	
	@Override
	public List<MediaCaption> mediaCaption() {
	
	return (List<MediaCaption>)mediaCaptionRepo.findAll();
	}

	@Override
	public List<Subscribe> subscribe(){ 
	return (List<Subscribe>) subscribeRepo.findAll();
	}
	
	@Override
	public ResponseEntity<?> getAllComment(Integer newsFeedId) {
	List<UserCommentResponse> responses = new ArrayList<>();	
	List<UserCommentDetails> userComment = userCommentRepo.findByNewsFeedId(newsFeedId);
	for (UserCommentDetails userCommentDetails : userComment) {
		UserCommentResponse userCommentResponse = new UserCommentResponse();
		if(userCommentDetails.getNewsFeedId().equals(newsFeedId)) {
			User userdetail = userRepo.findByUserId(userCommentDetails.getUserId());
			userCommentResponse.setUserId(userdetail.getUserId());
			userCommentResponse.setUserName(userdetail.getUserName());
			ProfilePic profilePic = profileRepo.findByUserId(userCommentDetails.getUserId());
			if(profilePic == null) {
				userCommentResponse.setProfilePic("null");
			}
			else 
			{
			userCommentResponse.setProfilePic(profilePic.getProfilePic());
			}
		}
		userCommentResponse.setNewsFeedId(userCommentDetails.getNewsFeedId());
		userCommentResponse.setComment(userCommentDetails.getComment());
		responses.add(userCommentResponse);
	 }	
	return new ResponseEntity(responses,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> LikeStatusAllfeed(Integer userId) {
	int count = 0;
	List<NewsFeed> newsFeedUrl = newsFeedRepo.findAll();
	Collections.reverse(newsFeedUrl);
	List<NewsFeedResponse> responses = new ArrayList<>();
	for (NewsFeed newsFeed : newsFeedUrl) {
		NewsFeedResponse newsFeedResponse =new NewsFeedResponse(); 
		newsFeedResponse.setNewsFeedId(newsFeed.getNewsFeedId());
		newsFeedResponse.setNewsFeedUrl(newsFeed.getNewsFeedUrl());		
		List<NewsFeedLikeDetails> newsFeedStatus = likeStatusAllFeedRepo.findByNewsFeedId(newsFeed.getNewsFeedId());
		if(newsFeedStatus.size()!=0 || newsFeedStatus.equals(null)) {
		for (NewsFeedLikeDetails newsFeedLikeDetails : newsFeedStatus) {
			count=newsFeedStatus.get(newsFeedStatus.size()-1).getLikeCount();
		    List<NewsFeedLikeDetails> userIdList=likeStatusAllFeedRepo.findByUserId(userId);	
			for (NewsFeedLikeDetails newsFeedLikeDetails2 : userIdList) {
				if((userId.equals(newsFeedLikeDetails2.getUserId()) && newsFeedLikeDetails.getNewsFeedId().equals(newsFeedLikeDetails2.getNewsFeedId()))) {		
				 newsFeedResponse.setUserId(newsFeedLikeDetails2.getUserId());
				 newsFeedResponse.setLikeType(newsFeedLikeDetails2.getLikeType());
				 newsFeedResponse.setLikeCount(count);
				}
				else
				{
				newsFeedResponse.setLikeCount(count);
				}
			 }
			responses.add(newsFeedResponse);
			break;
		   }
		}
		else 
		{
			responses.add(newsFeedResponse);
		}
	  }
	return new ResponseEntity(responses,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<?> UserComment(UserCommentRequest commentRequest) {
		UserCommentResponse response = new UserCommentResponse();
	    UserCommentDetails userCommentDetails = new UserCommentDetails();
		userCommentDetails.setUserId(commentRequest.getUserId());
		userCommentDetails.setNewsFeedId(commentRequest.getNewsFeedId());
		userCommentDetails.setComment(commentRequest.getComment());
		userCommentRepo.save(userCommentDetails);
		
		User userDetails = userRepo.findByUserId(commentRequest.getUserId());
		if((userDetails.getUserId()).equals(commentRequest.getUserId()))
		{
			response.setUserName(userDetails.getUserName());
		}	
		
		ProfilePic profileDetails = profileRepo.findByUserId(commentRequest.getUserId());
		try {
			if((profileDetails.getUserId()).equals(commentRequest.getUserId()))
			{
			response.setProfilePic(profileDetails.getProfilePic());
			}
		}catch(Exception e) {		
	   }				
		response.setNewsFeedId(userCommentDetails.getNewsFeedId());
		response.setUserId(commentRequest.getUserId());
		response.setComment(commentRequest.getComment());
	return new ResponseEntity<UserCommentResponse>(response,HttpStatus.OK);
	}
	
	@Override
	public UserQuizResponse saveQuizAnswer(QuizGameRequest request) {
		
		QuizAnswer quizAnswer = new QuizAnswer();
		quizAnswer.setQuizId(request.getQuizId());
		quizAnswer.setUserId(request.getUserId());
		quizAnswer.setQuizAnswer(request.getQuizAnswer());
		quizAnswerRepo.save(quizAnswer);
		
		UserQuizResponse response = new UserQuizResponse();
		response.setQuizId(request.getQuizId());
		response.setStatus(Status.SUCCESS);
		return response;
	}

	@Override
	public ResponseEntity<?> NewsFeedLike(LikeRequest likeRequest) {
		int likeCount = 0;
		int disLikeCount = 0;

		List<NewsFeedLikeDetails> pList = newsFeedLikeRepo.findByNewsFeedId(likeRequest.getNewsFeedId());
		for (NewsFeedLikeDetails postLikeDetails : pList) {
			if (postLikeDetails.getUserId().equals(likeRequest.getUserId())) {
				NewsFeedLikeDetails p = newsFeedLikeRepo.findByNewsFeedLikeId(postLikeDetails.getNewsFeedLikeId());
				newsFeedLikeRepo.delete(p);
			}
		}
		NewsFeedLikeDetails pDetails = new NewsFeedLikeDetails();
		pDetails.setNewsFeedId(likeRequest.getNewsFeedId());
		pDetails.setUserId(likeRequest.getUserId());
		pDetails.setLikeType(likeRequest.getLikeType());
		newsFeedLikeRepo.save(pDetails);
		List<NewsFeedLikeDetails> pList1 = newsFeedLikeRepo.findByNewsFeedId(likeRequest.getNewsFeedId());
		for (NewsFeedLikeDetails postLikeDetails : pList1) {
			if (postLikeDetails.getLikeType().equals("LIKE")) {
				likeCount++;
				pDetails.setLikeCount(likeCount);
			} else if (postLikeDetails.getLikeType().equals("DISLIKE") || postLikeDetails.getLikeType().equals(null)) {
				disLikeCount++;
			}
		}
		newsFeedLikeRepo.save(pDetails);
		LikeResponse lR = new LikeResponse();
		lR.setLikeCount(likeCount);
		lR.setDislikeCount(disLikeCount);
		lR.setLikeType(likeRequest.getLikeType());
		lR.setStatus(Status.SUCCESS);
		lR.setMessage("Post " + likeRequest.getLikeType() + " Successfully!!");
		return new ResponseEntity<LikeResponse>(lR, HttpStatus.OK);
	}

	@Override
	public List<Tournament> findInternationalUrl() {
	List<Tournament> path = tournamentRepo.findInternationalUrl();
	return path;
	}
	
	@Override
	public List<Tournament> findDomesticUrl() {
	List<Tournament> path = tournamentRepo.findDomesticUrl();
	return path;
	}

	@Override
	public List<Stats> findAllStats() {
		
	return (List<Stats>) statsRepo.findAll();
	}

	@Override
	public List<FitnessRoutine> findSchedule() {
		
	return (List<FitnessRoutine>) fitnessRoutineRepo.findAll();
	}

	@Override
	public List<VideoUrl> findVideoUrl() {
		
	return (List<VideoUrl>) videourlRepo.findAll();
	}
	
	@Override
	public String saveProfile(MultipartHttpServletRequest request, Integer userId) {
		String url = null;
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		try {
			String fileName = multiFile.getOriginalFilename();
			String extension = multiFile.getOriginalFilename().split("\\.")[1];
			String path = request.getServletContext().getRealPath("/");
			String serverPath = new File(".").getCanonicalPath();
			Properties prop = new Properties();
			String savedLocation =System.getProperty("user.dir")+"/src/main/webapp/views/images/Profile_Pic/";
			Date date = new Date();
			String name = date.getTime() + "NDA_" + "." + extension;
			byte[] bytes = multiFile.getBytes();
			File directory = new File(savedLocation);
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + name);		
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			String contentType = multiFile.getContentType();
			url ="http://"+request.getServerName()+":"+request.getLocalPort()+"/views/images/Profile_Pic/"+file.getName();
			ProfilePic profilePic = profilePicRepo.findByUserId(userId);
			if(profilePic == null) {
				ProfilePic pic = new ProfilePic();
				pic.setUserId(userId);
				pic.setProfilePic(url);
				profilePicRepo.save(pic);
			} else if(profilePic.getUserId().equals(userId)){
				profilePic.setUserId(userId);
				profilePic.setProfilePic(url);
				profilePicRepo.save(profilePic);
			} else
				{
				ProfilePic pic = new ProfilePic();
				pic.setUserId(userId);
				pic.setProfilePic(url);
				profilePicRepo.save(pic);
				}
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;	
	}

	@Override
	public List<Fixures> findAllFixures() {
		
	return (List<Fixures>)fixureRepo.findAll();
	}

	@Override
	public List<LiveMatch> findAllLiveMatch() {
		
	return (List<LiveMatch>)livematchRepo.findAll();
	}

	@Override
	public List<ProfilePic> getProfilePic() {
		
	return (List<ProfilePic>) profilePicRepo.findAll();
	}

	@Override
	public List<QuizQuestion> getQuizQuestion() {
		
	return (List<QuizQuestion>) quizQuestionRepo.findAll();
	}
}
