package org.sbang.service;

import java.util.Date;

import javax.inject.Inject;

import org.sbang.DTO.LoginDTO;
import org.sbang.domain.UserVO;
import org.sbang.mail.MailHandler;
import org.sbang.mail.TempKey;
import org.sbang.persistence.UserDAO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserServiceImpl implements UserService {

	@Inject
    private JavaMailSender mailSender;
	
	@Inject
	private UserDAO dao;

	@Override
	public void create(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.create(vo); // 회원가입
		String key = new TempKey().getKey(50, false); // 인증키 생성
		dao.createAuthKey(vo.getUserEmail(), key); // 인증키 DB저장
		MailHandler sendMail = new MailHandler(mailSender);
        sendMail.setSubject("[스방 홈페이지 이메일 인증]");
        sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
                .append("<a href='http://localhost/user/emailConfirm?userEmail=")
                .append(vo.getUserEmail())
                .append("&key=")
                .append(key)
                .append("' target='_blenk'>이메일 인증 확인</a>")
                .toString());
        sendMail.setFrom("beam2073@gmail.com", "스방관리자");
        sendMail.setTo(vo.getUserEmail());
        sendMail.send();

	}

	@Override
	public UserVO read(String userEmail) throws Exception {
		return dao.read(userEmail);
	}

	@Override
	public void update(UserVO vo) throws Exception {
		dao.update(vo);
	}
	
	@Transactional
	@Override
	public void delete(String userEmail) throws Exception {
		dao.delete(userEmail);
		dao.deleteAuth(userEmail);
	}

	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return dao.login(dto);
	}

	@Override
	public void changePwd(UserVO vo) throws Exception {
		dao.changePwd(vo);
	}

	@Override
	public boolean checkPw(String userEmail, String userPwd) {
		return dao.checkPw(userEmail, userPwd);
	}

	@Override
	public void keepLogin(String userEmail, String sessionId, Date next) throws Exception {
		dao.keepLogin(userEmail, sessionId, next);
	}

	@Override
	public UserVO checkLoginBefore(String value) {
		return dao.checkUserWithSessionKey(value);
	}

	@Override
	public void userAuth(String userEmail) throws Exception {
		dao.userAuth(userEmail);
	}
	
}