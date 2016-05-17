package de.mq.profile.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.mq.profile.domain.Image;

@WebServlet(urlPatterns ="/images")
public class ImageServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException {
    
		final String name = request.getParameter("fileName");
		
		if( name==null){
			return ;
		}
		
	
		
		@SuppressWarnings("unchecked")
		final Collection<Image> images =  (Collection<Image>) request.getSession().getAttribute("images");
		if( images == null){
			return ;
		}
		final Optional<Image> image = images.stream().filter(i -> i.getName().equals(name)).findAny();
		if(! image.isPresent()){
			return;
		}
		
		response.getOutputStream().write(image.get().getContent());
		
 }

}
