package org.sbang.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {

	private String userEmail;
	private String userPwd;
	private String userName;
	private String userBirth;
	private String userGender;
	private String userPhoneNumber;
	private String userIntroduce;
	private String userProfileImagePath;
	private String userRegion;
	private String userFavoriteCategory;
	private String userWishList;
	
	private Date userRegDate;
	private Date userUpdateDate;
	
	private String userGoogle;
	private String userNaver;
	
	private boolean userCertification;
	private boolean userActivityAlarm;
	private boolean userNewsAlarm;

}
