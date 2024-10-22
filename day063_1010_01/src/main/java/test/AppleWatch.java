package test;

public class AppleWatch implements Watch {

	public AppleWatch() {
		System.out.println("애플워치 생성자 호출");
	}
	
	@Override
	public void powerOn() {
		System.out.println("애플워치로 ON");
	}
	@Override
	public void powerOff() {
		System.out.println("애플워치로 OFF");
	}

}
