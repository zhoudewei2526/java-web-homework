/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.jsp;

import sample.model.UserLoginInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
	private List<UserLoginInfo> users =new ArrayList<UserLoginInfo>();
	private void addUser(UserLoginInfo user){
		this.users.add(user);
	};
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}
	@GetMapping("/login")
	public String login(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "login";
	}
	/**
	 * 校验login
	 * @param userName
	 * @param password
	 * @return
	 */
	@GetMapping("/doLogin")
	public String doLogin(String userName,String password) {
		boolean check = false;
		for(UserLoginInfo user:this.users){
			if(!check){
				check = user.volatileUser(userName,password);
			}else{
				break;
			}
		}
		return check?"successLogin":"failLogin";
	}
	@GetMapping("/register")
	public String register(Map<String, Object> model) {
		return "register";
	}
	@GetMapping("/doRegister")
	public String doRegister(String userName,String password) {
		UserLoginInfo newUser = new UserLoginInfo(userName,password);
		this.addUser(newUser);
		System.out.println(String.format("新用户注册成功，ID是:,%d", newUser.userId()));  
		return "successRegister";
	}
}
