package test;

import org.springframework.stereotype.Component;

@Component("gw")
public class GalaxyWatch implements Watch {

	public GalaxyWatch() {
		System.out.println("갤럭시워치 생성자 호출");
	}
	
	@Override
	public void powerOn() {
		System.out.println("갤럭시워치 ON");
	}
	@Override
	public void powerOff() {
		System.out.println("갤럭시워치 OFF");	
	}

}
