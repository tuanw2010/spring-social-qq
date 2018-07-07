/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.qq.connect;

import org.springframework.social.qq.api.QQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.qq.connect.javabeans.qzone.UserInfoBean;

public class QQAdapter implements ApiAdapter<QQ> {
	private static final Logger LOG = LoggerFactory.getLogger(QQAdapter.class);

	@Override
	public boolean test(QQ api) {
		try {
			api.qzoneUserInfoOperations().getUserInfo();
			return true;
		} catch (Exception e) {
			LOG.debug("", e);
			return false;
		}
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		try {
			values.setProviderUserId(api.getUserOpenID());
			
			UserInfoBean uib = api.qzoneUserInfoOperations().getUserInfo();
			values.setDisplayName(uib.getNickname());
			values.setProfileUrl(uib.getAvatar().getAvatarURL50());
			values.setImageUrl(uib.getAvatar().getAvatarURL50());
		} catch (Exception e) {
			LOG.error("error setConnectionValues", e);
		}
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		try {
			UserInfoBean uib = api.qzoneUserInfoOperations().getUserInfo();
			return new UserProfileBuilder().setName(uib.getNickname()).setUsername(uib.getNickname()).build();
		} catch (Exception e) {
			LOG.error("error fetchUserProfile", e);
		}
		return null;

	}

	@Override
	public void updateStatus(QQ api, String message) {
		try {
			api.weiboOperations().addWeibo(message);
		} catch (Exception e) {
			LOG.error("error updateStatus", e);
		}
	}

}
