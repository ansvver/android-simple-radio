package com.yabuhoo.server.utils;

import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yabuhoo.server.model.User;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class UserListXmlHandler extends DefaultHandler{
	
	public UserListXmlHandler(List<User> users) {
		this.users = users;
		// TODO Auto-generated constructor stub
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	//存放遍历集合
    private List<User> users;
    //构建Student对象
    private User user;
    //用来存放每次遍历后的元素名称(节点名称)
    private String tagName;
    
    //只调用一次  初始化list集合  
    @Override
    public void startDocument() throws SAXException {
    	//users=new ArrayList<User>();
    }
    
    
    //调用多次    开始解析
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if(qName.equals("user")){
            user=new User();
            //获取student节点上的id属性值
            //user.setId(Integer.parseInt(attributes.getValue(0)));
            //获取student节点上的group属性值
        }
        this.tagName=qName;
    }
    
    
    //调用多次  
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("user")){
            this.users.add(this.user);
        }
        this.tagName=null;
    }
    
    
    //只调用一次
    @Override
    public void endDocument() throws SAXException {
    }
    
    //调用多次
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if(this.tagName!=null){
            String date=new String(ch,start,length);
            if(this.tagName.equals("username")){
                this.user.setUsername(date);
            }
            else if(this.tagName.equals("password")){
                this.user.setPassword(date);
            }
        }
    }
    
	
}
