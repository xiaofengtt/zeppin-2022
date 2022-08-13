package cn.zeppin.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.access.MedalEx;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.Comment;
import cn.zeppin.entity.Medal;
import cn.zeppin.entity.Question;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserFriends;
import cn.zeppin.service.api.ICommentService;
import cn.zeppin.service.api.IMedalService;
import cn.zeppin.service.api.IQuestionService;
import cn.zeppin.service.api.IUserFriendsService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.utility.DictionaryRank;
import cn.zeppin.utility.Utlity;

public class PhoneMyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7718502193715088280L;

	private IMedalService medalService;
	private IQuestionService questionService;
	private ICommentService commentService;

	private IUserService userService;
	private IUserFriendsService userFriendService;

	private User currentUser;
	private String ranktitle;
	private List<MedalEx> medalExs = new ArrayList<MedalEx>();
	private List<Question> listQuestion = new ArrayList<Question>();

	private Question currentQuestion;
	private List<Comment> currentComments = new ArrayList<Comment>();

	private List<UserFriends> userFriends = new ArrayList<UserFriends>();

	/**
	 * 我的页面跳转
	 * 
	 * @return
	 */
	public String MyIndex() {

		User user = (User) session.getAttribute("userphone");

		// 根据
		this.ranktitle = DictionaryRank.getRankTitle(user.getScore());
		Map<String, Object> smap = new HashMap<String, Object>();
		smap.put("user.id", user.getId());
		List<Medal> medalList = this.getMedalService().getMedals(smap, -1, -1);

		for (Medal medal : medalList) {
			MedalEx medex = new MedalEx();
			short type = Short.valueOf(medal.getType().toString());
			medex.setName(Utlity.getOtherTypeName(type));
			if (type > 50 && type < 60) {
				medex.setType("safe-medal");
			} else if (type > 30 && type < 40) {
				medex.setType("psy-medal");
			} else if (type > 40 && type < 50) {
				medex.setType("learn-medal");
			}
			medalExs.add(medex);
		}

		// 获取问题列表
		List<Question> liQus = this.getQuestionService().getQuestionByMap(null, -1, -1);
		this.listQuestion.addAll(liQus);

		currentUser = user;
		return "my";
	}

	/**
	 * 添加问题
	 * 
	 * @return
	 */
	public String AddQuestion() {

		String content = request.getParameter("text");
		User user = (User) session.getAttribute("userphone");

		if (content != null && !content.equals("")) {
			Question q = new Question();
			q.setContent(content);
			q.setUser(user);
			q.setCount(0);
			this.getQuestionService().addQuestion(q);
		}

		return "myAdd";

	}

	/**
	 * 回答问题页面
	 * 
	 * @return
	 */
	public String Ansque() {

		int qid = this.getIntValue(request.getParameter("qid"));

		Question question = this.getQuestionService().getQuestion(qid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("qid", qid + "");

		List<Comment> licoms = this.getCommentService().getCommentByMap(map, -1, -1);

		currentQuestion = question;
		this.currentComments.addAll(licoms);

		return "answer";
	}

	public String AddComment() {
		String content = request.getParameter("text");
		int qid = this.getIntValue(request.getParameter("qid"));

		User user = (User) session.getAttribute("userphone");
		Question question = this.getQuestionService().getQuestion(qid);

		if (content != null && !content.equals("")) {

			Comment comment = new Comment();
			comment.setContent(content);
			comment.setQuestion(question);
			comment.setUser(user);

			this.getCommentService().AddComment(comment);

			question.setCount(question.getCount() + 1);

			this.questionService.updateQuestion(question);

		}

		return "myAnswer";
	}

	/**
	 * 添加好友
	 */
	public void AddFriend() {

		User user = (User) session.getAttribute("userphone");
		ActionResult result = new ActionResult();

		String userid = this.request.getParameter("userid");
		String type = this.request.getParameter("type");

		int user_id = this.getIntValue(userid);
		short type_id = Short.valueOf(type);

		User friend = this.userService.getUserById(user_id);

		if (friend == null) {
			result.init(ERROR_STATUS, "添加好友不存在", null);

		} else if (friend.getId() == user.getId()) {
			result.init(ERROR_STATUS, "自己不能添加自己", null);
		} else if (this.userFriendService.checkFriend(user.getId(), user_id)) {
			result.init(ERROR_STATUS, "已经添加好友", null);
		} else {

			UserFriends uf = new UserFriends();
			uf.setType(type_id);
			uf.setUserByUser(user);
			uf.setUserByFriend(friend);

			this.userFriendService.addFriends(uf);

			result.init(SUCCESS_STATUS, "添加好友成功", null);

		}

		Utlity.ResponseWrite(result, null, response);

	}

	/**
	 * 获取好友列表
	 * 
	 * @return
	 */
	public String GetFriends() {

		User user = (User) session.getAttribute("userphone");

		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getId().toString());

		List<UserFriends> liUfs = this.userFriendService.getFriends(map, -1, -1);

		if (liUfs != null && !liUfs.isEmpty()) {
			this.userFriends.addAll(liUfs);
		}

		return "friends";

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public List<MedalEx> getMedalExs() {
		return medalExs;
	}

	public void setMedalExs(List<MedalEx> medalExs) {
		this.medalExs = medalExs;
	}

	public IMedalService getMedalService() {
		return medalService;
	}

	public void setMedalService(IMedalService medalService) {
		this.medalService = medalService;
	}

	public String getRanktitle() {
		return ranktitle;
	}

	public void setRanktitle(String ranktitle) {
		this.ranktitle = ranktitle;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public ICommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}

	public List<Question> getListQuestion() {
		return listQuestion;
	}

	public void setListQuestion(List<Question> listQuestion) {
		this.listQuestion = listQuestion;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public List<Comment> getCurrentComments() {
		return currentComments;
	}

	public void setCurrentComments(List<Comment> currentComments) {
		this.currentComments = currentComments;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserFriendsService getUserFriendService() {
		return userFriendService;
	}

	public void setUserFriendService(IUserFriendsService userFriendService) {
		this.userFriendService = userFriendService;
	}

	public List<UserFriends> getUserFriends() {
		return userFriends;
	}

	public void setUserFriends(List<UserFriends> userFriends) {
		this.userFriends = userFriends;
	}

}
