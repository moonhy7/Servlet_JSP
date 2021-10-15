package spms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

//애노테이션 방법
@WebFilter(
		urlPatterns = "/*",
		initParams = {
				@WebInitParam(name = "encoding", value = "UTF-8")
		}
)

public class CharacterEncodingFilter implements Filter {
	FilterConfig config;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter) throws ServletException, IOException {
		//필터 처리 작업은 다음 필터로 전송 전에 작성
		request.setCharacterEncoding(config.getInitParameter("encoding"));
		//다음 필터로 전송하는 메소드
		//다음 필터가 존재하지 않으면 서블릿의 service() 메소드 호출
		nextFilter.doFilter(request, response);
	}
	
	@Override
	public void destroy() {}
}
