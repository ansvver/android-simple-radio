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
	//��ű�������
    private List<User> users;
    //����Student����
    private User user;
    //�������ÿ�α������Ԫ������(�ڵ�����)
    private String tagName;
    
    //ֻ����һ��  ��ʼ��list����  
    @Override
    public void startDocument() throws SAXException {
    	//users=new ArrayList<User>();
    }
    
    
    //���ö��    ��ʼ����
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if(qName.equals("user")){
            user=new User();
            //��ȡstudent�ڵ��ϵ�id����ֵ
            //user.setId(Integer.parseInt(attributes.getValue(0)));
            //��ȡstudent�ڵ��ϵ�group����ֵ
        }
        this.tagName=qName;
    }
    
    
    //���ö��  
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("user")){
            this.users.add(this.user);
        }
        this.tagName=null;
    }
    
    
    //ֻ����һ��
    @Override
    public void endDocument() throws SAXException {
    }
    
    //���ö��
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
