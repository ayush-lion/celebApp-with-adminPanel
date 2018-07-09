package com.ctn.celebApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctn.celebApp.entity.AboutMe;
import com.ctn.celebApp.entity.Collaboration;
import com.ctn.celebApp.entity.Fanboard;
import com.ctn.celebApp.entity.FeedBack;
import com.ctn.celebApp.entity.FitnessRoutine;
import com.ctn.celebApp.entity.Fixures;
import com.ctn.celebApp.entity.LiveMatch;
import com.ctn.celebApp.entity.MediaCaption;
import com.ctn.celebApp.entity.MyDiet;
import com.ctn.celebApp.entity.MyProfile;
import com.ctn.celebApp.entity.PostUrl;
import com.ctn.celebApp.entity.PostVideos;
import com.ctn.celebApp.entity.Quizgame;
import com.ctn.celebApp.entity.Stats;
import com.ctn.celebApp.entity.Subscribe;
import com.ctn.celebApp.entity.Tournament;
import com.ctn.celebApp.entity.VideoUrl;
import com.ctn.celebApp.userrequest.EmailRequest;
import com.ctn.celebApp.userrequest.LikeRequest;
import com.ctn.celebApp.userrequest.LoginWithFacebookRequest;
import com.ctn.celebApp.userrequest.LoginWithGoogle;
import com.ctn.celebApp.userrequest.PostUrlRequest;
import com.ctn.celebApp.userrequest.QuizGameRequest;
import com.ctn.celebApp.userrequest.UserCommentRequest;
import com.ctn.celebApp.userrequest.UserCreateRequest;
import com.ctn.celebApp.userrequest.UserLoginRequest;
import com.ctn.celebApp.userrequest.VideoUrlRequest;
import com.ctn.celebApp.userresponse.StatusResponse;
import com.ctn.celebApp.userresponse.UserDetailsResponse;
import com.ctn.celebApp.userresponse.UserQuizResponse;

public interface UserService {

	public UserDetailsResponse create(UserCreateRequest userCreateRequest);

	public UserDetailsResponse login(UserLoginRequest userLoginRequest);

	public UserDetailsResponse loginWithFacebook(LoginWithFacebookRequest loginWithFacebookRequest);

	public UserDetailsResponse loginWithGoogle(LoginWithGoogle loginWithGoogle);

	public StatusResponse forgotPassword(EmailRequest emailRequest);

	public ResponseEntity<?> NewsFeedLike(LikeRequest likeRequest);

	public List<MyDiet> myDiet();

	public List<Subscribe> subscribe();

	public List<MediaCaption> mediaCaption();

	public List<Tournament> findInternationalUrl();

	public List<Tournament> findDomesticUrl();

	public List<Stats> findAllStats();

	public List<FitnessRoutine> findSchedule();

	public List<VideoUrl> findVideoUrl();

	public List<Quizgame> findquiz();

	public ResponseEntity<?> LikeStatusAllfeed(Integer userId);

	public ResponseEntity<?> UserComment(UserCommentRequest commentRequest);

	public ResponseEntity<?> getAllComment(Integer newsFeedId);

	public UserQuizResponse saveQuizAnswer(QuizGameRequest request);

	public String saveProfile(MultipartHttpServletRequest request, Integer userId);

	public List<Fixures> findAllFixures();

	public List<LiveMatch> findAllLiveMatch();

}
