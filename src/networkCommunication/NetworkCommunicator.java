package networkCommunication;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import Utilities.CountDownTimer;
import Utilities.IOSystem;

public class NetworkCommunicator {

	private static final int port = 12345;

	/**
	 * 
	 * @param ipAddress
	 * @return a socket that is connected to another socket
	 * The socket is null if the connection failed or 
	 * there is a timeout and the socket is not yet connected
	 */
	public static Socket connect (final String ipAddress) {
		
		
		// start the timer to avoid hanging
		CountDownTimer timer = new CountDownTimer(5);
		new Thread(timer).start();
		
		// for the anonymous class problem in java1.8-
		class SocketWrapper {
			Socket socket = null;
		}

		// start making the connection
		final SocketWrapper socketWrapper = new SocketWrapper();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					socketWrapper.socket = new Socket(ipAddress, port); // may hang here
				} catch (IOException e) {
					// do nothing
//				    System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
				}
			}
		}).start();
		
		// either of the 2 conditions lets the program skip the
		// while loop below and proceed: timeout occurs or socket gets connected
		while (true) {
			if (timer.isTimeOut() || socketWrapper.socket != null) {
				break;
			}
			try {
				Thread.sleep(100); // avoid dominating the resources
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		return socketWrapper.socket;
		
		/* simplified implementation: rely on the timeout provided by java
		Socket socket = null;
		try{
			socket = new Socket(ipAddress, port); // may hang here
			return socket;
		} catch (IOException e) {
			System.out.println("Connection error");
			return null;
		}
		*/
	}
	
	public static Socket getConnected(int waitSec) {
		Socket socket = null;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(waitSec * 1000);
			socket = serverSocket.accept();
		} catch (SocketTimeoutException e) {
			System.out.println("Timed out");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return socket;
	}
	
	public static void sendDataPackage(Socket socket, DataPackage dataPackage) {
		if (socket == null || dataPackage == null) return;
		
		byte[] info = IOSystem.getByteArray(dataPackage);
		try {
			socket.getOutputStream().write(info);
			socket.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static DataPackage receiveDataPackage(Socket socket) {
		DataPackage toBeReturned = null;
		while (true) {
			try {
				if (socket.getInputStream().available() > 0) {
					InputStream is = socket.getInputStream();
					byte[] response = new byte[is.available()];
					is.read(response);
					toBeReturned = (DataPackage) IOSystem.getObject(response);
					break;
				}

				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return toBeReturned;
	}
	
	/*
	
	 * false IP will make it hang...
	 * @return
	public static boolean checkConnection() {
		
		// a wrapper class so that connection variable can be modified in an anonymous class
		class ImmutableBoolean {
			boolean connected = false;
		}
		ImmutableBoolean internentConnection = new ImmutableBoolean();
		
		// a runnable object that checks 
		Thread connectionThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// if no server is running on this ip, the following line will hang
					// and the connected variable will always be false;
					Socket socket = new Socket(ipAddress, port); 
					internentConnection.connected = true;
				} catch (IOException e) {
//					System.out.println("No internet connection."); // connected variable is unchanged; it is false
				} 
			}
			
		});
		connectionThread.start();
		
		// wait for 3 seconds. if there is no response from server, the variable will be false. 
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return internentConnection.connected;
		
		
	}
	*/
	
}

