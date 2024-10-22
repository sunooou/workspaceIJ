package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("apple")
public class IPhone implements Phone {
	@Autowired // 메모리의 자료형(타입)을 인지해서 주입해줌
	@Qualifier("aw")
	private Watch watch; // DI 대상
	
	public IPhone() {
		System.out.println("아이폰 객체 생성 01");
	}
	/*
	public IPhone(Watch watch) {
		this.watch=watch;
		System.out.println("아이폰 객체 생성 02");
	}
	public IPhone(Watch watch,int num) {
		this.watch=watch;
		this.num=num;
		System.out.println("아이폰 객체 생성 03");
	}
	*/

	@Override
	public void powerOn() {
		this.watch.powerOn();
	}
	@Override
	public void powerOff() {
		this.watch.powerOff();
	}

	public Watch getWatch() {
		return watch;
	}
	public void setWatch(Watch watch) {
		this.watch = watch;
	}
}
