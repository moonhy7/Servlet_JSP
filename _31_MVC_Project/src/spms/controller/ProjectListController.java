package spms.controller;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.ProjectDAO;

@Component("/project/list.do")
public class ProjectListController implements Controller {
	ProjectDAO projectDAO;
	
	public ProjectListController setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("projects", projectDAO.selectlist());
		return "/project/ProjectList.jsp";
	}
}
