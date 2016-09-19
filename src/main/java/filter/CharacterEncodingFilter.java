/**
 * 
 */
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p><b>Description:</b> 字符编码过滤器</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Date:</b> 2016年5月15日 上午10:41:25</p>
 * @author Saka
 * @version v1.0
 */
public class CharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig;//用于初始化过滤器的FilterConfig，对应于web.xml的一个<filter>与<filter-mapping>标签
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String encode = filterConfig.getInitParameter("encode");
		((HttpServletRequest)servletRequest).setCharacterEncoding(encode);
		((HttpServletResponse)servletResponse).setCharacterEncoding(encode);
		servletResponse.setContentType("text/html");//不要忘记设置相应类型
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig=filterConfig;//在此处获取web.xml中filter配置的上下文，初始化filterConfig对象
	}

}
