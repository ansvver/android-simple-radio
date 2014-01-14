package com.yabuhoo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.yabuhoo.server.model.User;
import com.yabuhoo.server.utils.UserListXmlHandler;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class Server implements Runnable {

	
	private static Set<String>	online_ips  = new HashSet<String>();
	static HashMap<String, Pool> pools = new HashMap<String, Pool>();
	//static HashMap<String, String> map = new HashMap<String, String>();
	private static List<User> users = new ArrayList<User>();
	private Socket socket = null;
	
	public Server(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			byte[] b = new byte[100];
			in.read(b);
			String cmd = new String(b).trim();
			if(cmd.startsWith(Globals.LOGIN_CMD_PREFIX)) {
				//login,pang,8888
				String [] str = cmd.split(Globals.CMD_SEPARATOR);
				User u = new User();
				u.setUsername(str[1]);
				u.setPassword(str[2]);
System.out.println(str[1] +  "------->" + socket.getInetAddress().getHostAddress());
				for(User uu : users) {
					if(uu.equals(u)) {
						out.write(Globals.SUCCESS.getBytes());
						out.flush();
						online_ips.add(socket.getInetAddress().getHostAddress());
							//这里存了在线的用户，不过并没有做用户退出与否的状态的判断，后续可加上。
						return;							
					}
				}
				out.write(Globals.FAILURE.getBytes());
				out.flush();
			}else if(cmd.startsWith(Globals.GET_FROM_SERVER_CMD_PREFIX)){
				try {
					//get,123456789.amr
System.out.println(cmd);
					String filename = cmd.split(Globals.CMD_SEPARATOR)[1];
					File f = new File(Globals.AMR_FILE_FOLDER, filename);
					if(f.exists()) {
System.out.println("file exists!");
						out.write((Globals.SUCCESS + Globals.CMD_SEPARATOR +f.length()).getBytes());
						out.flush();
						byte[] b1 = new byte[10];
						in.read(b1);
						FileInputStream fin = new FileInputStream(f);
						int len = 0;
						byte[] b2 = new byte[1024];
						while((len=fin.read(b2))!=-1) {
							out.write(b2, 0, len);
							out.flush();
						}
						fin.close();
						return;
					}
					throw new Exception();
				} catch (Exception e) {
System.out.println("no such file!");
					out.write(Globals.FAILURE.getBytes());
					out.flush();
				}
				
			}else if(cmd.startsWith(Globals.UPLOAD_TO_SERVER_CMD_PREFIX)){
				//upload,14224,pang
				String s = cmd.split(Globals.CMD_SEPARATOR)[1];
				String username = cmd.split(Globals.CMD_SEPARATOR)[2];
				long size = Long.parseLong(s);
				out.write(Globals.SUCCESS.getBytes());
				out.flush();
				File ff = new File(Globals.AMR_FILE_FOLDER, (new Date().getTime()) + "R" 
						+ ((int) (Math.random() * 100000)) + ".amr");
				FileOutputStream fout = new FileOutputStream(ff);
				byte[] b1 = new byte[1024];
				int len = 0;
				long length = 0;
				while((len=in.read(b1)) != -1) {
					length += len;
					fout.write(b1, 0, len);
					if(length >= size) {
						break;
					}
					
				}
				fout.close();
				
				Pool p = new Pool();
				p.message = username + Globals.CMD_SEPARATOR + ff.getName();
				pools.put(p.id, p);
				
				for(String ip : online_ips) {
					if(ip.equalsIgnoreCase(socket.getInetAddress().getHostAddress()))
						continue;
					p.ips.add(ip);
				}
				
				p.start();
		
			}
		} catch (Exception e) {
			
		} finally {
			try {
				socket.close();
			} catch (Exception e1) {
			}
			
		}
		
	}

	public static void openServer() throws Exception{
		//List<User> users = new ArrayList<User>();
		try{
			File f = new File(Globals.USER_INFO_XML_FILE);
			FileInputStream fin = new FileInputStream(f);
			int x = fin.available();
	        byte b[] = new byte[x];
	        fin.read(b);
	        String xmlDocument = new String(b);
			fin.close();
System.out.println(xmlDocument);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			reader.setContentHandler(new UserListXmlHandler(users));
			reader.parse(new InputSource(new StringReader(xmlDocument)));
		}
		catch(Exception e){
			e.printStackTrace();
		}
System.out.println(users.size());
		for(User u : users) {
			System.out.println("User------->" + u.getUsername() + "/" + u.getPassword());
		}
		
		
		ServerSocket server = new ServerSocket(Globals.SERVER_SOCKET_PORT);
		while (true) {
			new Thread(new Server(server.accept())).start();
			System.out.println("Connected！");
		}
	}
}
