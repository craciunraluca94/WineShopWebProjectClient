package com.wineshop.beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.wineshop.dao.PlatformUserDAORemote;
import com.wineshop.dto.LoginDTO;
import com.wineshop.dto.PlatformUserDTO;
import com.wineshop.exceptions.ChangePasswordException;
import com.wineshop.exceptions.LoginException;

@ManagedBean
@RequestScoped
public class LoginBean {

	static final Logger LOGGER = Logger.getLogger(LoginBean.class.getName());
	LoginDTO loginDTO = new LoginDTO();
	PlatformUserDTO platformUserDTO = new PlatformUserDTO();

	@EJB
	PlatformUserDAORemote userDAORemote;

	/**
	 * Default constructor.
	 */
	public LoginBean() {
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public String login() {
		LOGGER.log(Level.INFO, "Trying to login with the credentials: ", loginDTO.toString());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			platformUserDTO = userDAORemote.login(loginDTO);
			LOGGER.log(Level.INFO, "Successfully logged in with:" + loginDTO.toString());
			facesContext.getExternalContext().getFlash().put("user", platformUserDTO);
			facesContext.getExternalContext().redirect("changePassword.xhtml");
			return "success";
		} catch (LoginException | IOException e) {
			if (e.getClass().equals(IOException.class))
				LOGGER.log(Level.SEVERE, "An error occurred while redirecting to the change password page: ", e);
			facesContext.addMessage("loginForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
	}
}
