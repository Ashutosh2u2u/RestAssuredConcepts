package files;

import java.util.List;

public class CoursesJson {
	
	private List<WebAutomation> webAutomation;//expect a list under WebAutomation as of courseTitle,price
	private List<Api> api;
	private List<Mobile> mobile;

	public List<WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
	
	
		
}
