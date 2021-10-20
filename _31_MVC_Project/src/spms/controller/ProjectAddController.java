package spms.controller;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDAO;
import spms.vo.Project;

@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding {
	ProjectDAO projectDAO;
	
	public ProjectAddController setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"project", spms.vo.Project.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project)model.get("project");
		if(project.getTitle() == null) {
			return "/project/ProjectForm.jsp";
		} else {
			projectDAO.insert(project);
			return "redirect:list.do";
		}
	}
}
