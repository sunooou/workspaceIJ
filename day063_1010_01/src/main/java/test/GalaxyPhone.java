package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("samsung")
public class GalaxyPhone implements Phone {
	@Autowired // DI == 의존 주입
	@Qualifier("gw")
	private Watch watch;
	
	public GalaxyPhone() {
		System.out.println("갤럭시 객체 생성 01");
	}
	public GalaxyPhone(Watch watch) {
		this.watch=watch;
		System.out.println("갤럭시 객체 생성 02");
	}

	@Override
	public void powerOn() {
		this.watch.powerOn();
	}
	@Override
	public void powerOff() {
		this.watch.powerOff();
	}	
}
