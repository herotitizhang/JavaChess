package Utilities;

public class CountDownTimer implements Runnable {

	private boolean timeOut = false;
	private int seconds = 10;
	
	public CountDownTimer(int seconds) {
		this.seconds = seconds;
	}
	
	public void run() {
		try {
			Thread.sleep(seconds * 1000); // wait for 10 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timeOut = true;
	}

	public boolean isTimeOut() {
		return timeOut;
	}
}