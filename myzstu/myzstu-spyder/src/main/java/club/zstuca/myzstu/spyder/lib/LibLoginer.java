package club.zstuca.myzstu.spyder.lib;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//图书馆登录（暂时废弃）
@Component
public class LibLoginer {
	public Map<String, Object> login(String username, String password) throws Exception {
		Map<String, Object> res = new HashMap<>();
		return res;
	}

	//填充请求信息
	private List<NameValuePair> inputLoginForm(String username, String password, String execution) {
		List<NameValuePair> formData = new ArrayList<NameValuePair>();
		formData.add(new BasicNameValuePair("username", username));
		formData.add(new BasicNameValuePair("password", password));
		formData.add(new BasicNameValuePair("passwordPre", password));
		formData.add(new BasicNameValuePair("type", "UsernamePassword"));
		formData.add(new BasicNameValuePair("_eventId", "submit"));
		formData.add(new BasicNameValuePair("execution", execution));
		formData.add(new BasicNameValuePair("geolocation", ""));
		formData.add(new BasicNameValuePair("captcha_code", ""));
		return formData;
	}
}
