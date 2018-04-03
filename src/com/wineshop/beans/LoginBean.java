package com.wineshop.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.wineshop.dao.PlatformUserDAORemote;
import com.wineshop.dto.LoginDTO;

@ManagedBean
@RequestScoped
public class LoginBean {

	LoginDTO loginDTO;

	@EJB
	PlatformUserDAORemote userDAORemote;

	/**
	 * Default constructor.
	 */
	public LoginBean() {
	}

	@PostConstruct
	public void init() {
		loginDTO = new LoginDTO();
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		System.out.println("Login: " + loginDTO.getUsername() + " " + loginDTO.getPassword());
		return "";
	}
}
