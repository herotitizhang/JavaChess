package Utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import javax.imageio.ImageIO;


public class IOSystem {
	
	/**
	 * get a scaled image
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Image getScaledImage (URL path, int width, int height) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	/**
	 * mostly close an inputstream or an outputstream
	 * @param toBeClosed
	 */
	public static void closeCloseable(Closeable toBeClosed) {
		if (toBeClosed != null) {
			try {
				toBeClosed.close();
			} catch (IOException e) {
				System.out.println(toBeClosed.getClass());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * convert an object to a byte array
	 * @param obj
	 * @return
	 */
	public static byte[] getByteArray(Object obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeCloseable(oos);
		}
		
		return baos.toByteArray();
	}

	/**
	 * convert a byte array to an object
	 * @param byteArray
	 * @return
	 */
	public static Object getObject(byte[] byteArray) {
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeCloseable(ois);
		}
		return null;
	}
	
	public static String getPublicIPAddress() {
		String publicIPAddreess = null;
		try {
			URL amazonURL = new URL("http://checkip.amazonaws.com");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
	                amazonURL.openStream()));
			publicIPAddreess = reader.readLine(); //you get the IP as a String
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publicIPAddreess;

		
//	    String res = null;
//	    try {
//	        String localhost = InetAddress.getLocalHost().getHostAddress();
//	        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
//	        while (e.hasMoreElements()) {
//	            NetworkInterface ni = (NetworkInterface) e.nextElement();
//	            if(ni.isLoopback())
//	                continue;
//	            if(ni.isPointToPoint())
//	                continue;
//	            Enumeration<InetAddress> addresses = ni.getInetAddresses();
//	            while(addresses.hasMoreElements()) {
//	                InetAddress address = (InetAddress) addresses.nextElement();
//	                if(address instanceof Inet4Address) {
//	                    String ip = address.getHostAddress();
//	                    if(!ip.equals(localhost))
//	                        System.out.println((res = ip));
//	                }
//	            }
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return res;
	}
	
}
