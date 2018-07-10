package com.ctn.celebApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctn.celebApp.entity.FitnessRoutine;
import com.ctn.celebApp.entity.Fixures;
import com.ctn.celebApp.entity.LiveMatch;
import com.ctn.celebApp.entity.MediaCaption;
import com.ctn.celebApp.entity.MyDiet;
import com.ctn.celebApp.entity.ProfilePic;
import com.ctn.celebApp.entity.QuizQuestion;
import com.ctn.celebApp.entity.Stats;
import com.ctn.celebApp.entity.Tournament;
import com.ctn.celebApp.entity.VideoUrl;
import com.ctn.celebApp.service.UserService;
import com.ctn.celebApp.userrequest.EmailRequest;
import com.ctn.celebApp.userrequest.LikeRequest;
import com.ctn.celebApp.userrequest.LoginWithFacebookRequest;
import com.ctn.celebApp.userrequest.LoginWithGoogle;
import com.ctn.celebApp.userrequest.QuizGameRequest;
import com.ctn.celebApp.userrequest.UserCommentRequest;
import com.ctn.celebApp.userrequest.UserCreateRequest;
import com.ctn.celebApp.userrequest.UserLoginRequest;
import com.ctn.celebApp.userresponse.StatusResponse;
import com.ctn.celebApp.userresponse.UserDetailsResponse;
import com.ctn.celebApp.userresponse.UserQuizResponse;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	/**************************************** Create A User ***********************************************/

	@RequestMapping(value = "/create", produces = "application/json", method = RequestMethod.POST)
	public UserDetailsResponse userCreation(@RequestBody UserCreateRequest userCreateRequest) {
		
	return userService.create(userCreateRequest);
	}

	/**************************************** Login A User ************************************************/

	@RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.POST)
	public UserDetailsResponse login(@RequestBody UserLoginRequest userLoginRequest) {

	return userService.login(userLoginRequest);
	}

	/**************************************** Login With Facebook *****************************************/

	@RequestMapping(value = "/loginWithFacebook", produces = "application/json", method = RequestMethod.POST)
	public UserDetailsResponse loginWithFacebook(@RequestBody LoginWithFacebookRequest loginWithFacebookRequest) {

	return userService.loginWithFacebook(loginWithFacebookRequest);
	}
	
	/**************************************** Login With Google *******************************************/
	
	@RequestMapping(value = "/loginWithGoogle", produces = "application/json", method = RequestMethod.POST)
	public UserDetailsResponse loginWithGoogle(@RequestBody LoginWithGoogle loginWithGoogle) {
		
	return userService.loginWithGoogle(loginWithGoogle);
	}
	
	/***************************************** ChooseQuiz **********************************************/
	@RequestMapping(value="/quizChoose", produces = "application/json", method = RequestMethod.POST)
	public UserQuizResponse saveQuizAnswer(@RequestBody QuizGameRequest request) {
	
	return userService.saveQuizAnswer(request);	
	}
	
	/**************************************** QuizQuestion ******************************************************/
	@RequestMapping(value = "/quizQuestion", produces = "application/json", method = RequestMethod.GET)
	public List<QuizQuestion> getQuizQuestion(){
		
	return userService.getQuizQuestion();	
	}
	
	/****************************************** MediaCaption **********************************************/
	@RequestMapping(value = "/mediaCaption", produces = "application/json", method = RequestMethod.GET)
	public List<MediaCaption> mediaCaption(){
	
	return userService.mediaCaption();
	}
	
	/****************************************** schedule ****************************************************/
	
	@RequestMapping(value = "/schedule", produces = "application/json", method = RequestMethod.GET)
	public List<FitnessRoutine> findSchedule(){
	
	return userService.findSchedule();	
	}
	
	/****************************************** mydiet ****************************************************/
		
	@RequestMapping(value = "/mydiet", produces = "application/json", method = RequestMethod.GET)
	public List<MyDiet> myDiet(){
			
	return userService.myDiet();
	}
	
	/****************************************** youtube ****************************************************/
	
	@RequestMapping(value = "/youtube", produces = "application/json", method = RequestMethod.GET)
	public List<VideoUrl> findVideoUrl(){
		
	return userService.findVideoUrl();	
	}
	
	/**************************************** international ***********************************************/
	@RequestMapping(value = "/international", produces ="application/json", method = RequestMethod.GET)
	public List<Tournament> findInternationalUrl(){
	
	return userService.findInternationalUrl();	
	}
	
	/**************************************** domestic ***********************************************/
	@RequestMapping(value = "/domestic", produces ="application/json", method = RequestMethod.GET)
	public List<Tournament> findDomesticUrl(){
	
	return userService.findDomesticUrl();	
	}
	
	/*************************************** Tournament stats *************************************************/
	@RequestMapping(value = "/stats/{id}", method = RequestMethod.GET)
	public List<Stats> findById(@PathVariable Integer id){
	List<Stats> stat = new 	ArrayList<>();
	List<Stats> stats = userService.findAllStats();
	for (Stats stats2 : stats) {
		if (stats2.getId()==id) {
			stat.add(stats2);
			break;
		}
	}
	return 	stat;
	}
	
    /***************************************** get Profile Pic ******************************************/
	
	@RequestMapping(value="/getProfilePic/{userId}", method = RequestMethod.GET)
	public List<ProfilePic> getProfilePic(@PathVariable Integer userId) {
	List<ProfilePic> result = new ArrayList<>();
	List<ProfilePic> pro = userService.getProfilePic();
	for (ProfilePic profilePic : pro) {
		if(profilePic.getUserId()==userId) {
			result.add(profilePic);
		}
		else
		if(profilePic.getUserId() != userId) {
			
		}
	}
	return 	result;
	}	
	
	/******************************************** News Feed Like ********************************************/
	
	@RequestMapping(value="/NewsFeedLike", method=RequestMethod.POST)
	public ResponseEntity<?> NewsFeedLike(@RequestBody LikeRequest likeRequest){

	return userService.NewsFeedLike(likeRequest);
	}
	
	/***************************************** Comment Post ***************************************************/
	
	@RequestMapping(value ="/PostComment", method = RequestMethod.POST)
	public ResponseEntity<?> UserComment(@RequestBody UserCommentRequest commentRequest){

	return userService.UserComment(commentRequest);	
	}
	
	/******************************************** News Feed ********************************************/
	
	@RequestMapping(value="/newsFeed/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> likeStatusAllfeed(@PathVariable Integer userId){

	return userService.LikeStatusAllfeed(userId);	
	}
	
    /******************************************** Comment ********************************************/
	
	@RequestMapping(value ="/getComment/{newsFeedId}",method = RequestMethod.GET)
	public ResponseEntity<?> getComment(@PathVariable Integer newsFeedId){
		
	return userService.getAllComment(newsFeedId);	
	}
	
	/****************************************** Forgot Password *******************************************/
	
	@RequestMapping(value = "/forgotPassword",  produces = "application/json", method = RequestMethod.PUT)
	public StatusResponse forgotPassword(@RequestBody EmailRequest emailRequest) {
		
	return userService.forgotPassword(emailRequest);
	}
	
	/**************************************** set Profile pic ******************************************/
	
	@RequestMapping(value="/setProfilePic", method=RequestMethod.POST)
	public String saveProfile(MultipartHttpServletRequest request,Integer userId){

	return userService.saveProfile(request,userId);
	}
	
	/***************************************** Fixures ***************************************************/
	
	@RequestMapping(value="/fixures", method=RequestMethod.GET)
	public List<Fixures> findAllFixures(){
		
	return userService.findAllFixures();			
	}
	
	/**************************************** LiveMatch *************************************************/
	@RequestMapping(value="/liveMatch",method=RequestMethod.GET)
	public List<LiveMatch> findAllLiveMatch(){
		
	return userService.findAllLiveMatch();	
	}
}