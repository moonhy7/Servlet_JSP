package spms.controller;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDAO;
import spms.vo.Project;

@Component("/project/update.do")
public class ProjectUpdateController implements Controller, DataBinding {
	ProjectDAO projectDAO;
	
	public ProjectUpdateController setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"project", spms.vo.Project.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project)model.get("project");
		
		if(project.getTitle() == null) {
			Integer no = (Integer)model.get("no");
			Project detailInfo = projectDAO.selectOne(no);
			model.put("project", detailInfo);
			return "/project/ProjectUpdateForm.jsp";
		} else {
			projectDAO.update(project);
			return "redirect:list.do";
		}
	}
}
